package com.example.rakib;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class SnakeView extends TileView {

    private int mMode = READY;
    public static final int PAUSE = 0;
    public static final int READY = 1;
    public static final int RUNNING = 2;
    public static final int LOSE = 3;

    private int mDirection = NORTH;
    private int mNextDirection = NORTH;
    private static final int NORTH = 1;
    private static final int SOUTH = 2;
    private static final int EAST = 3;
    private static final int WEST = 4;

    private static final int BODY = 1;
    private static final int HEAD = 2;
    private static final int APPLE = 3;
    private static final int BORDER = 4;
    
    public static int mScore = 0;
    private int mMoveDelay = 300;
    
    private long mLastMove;

    private TextView mStatusText;

    private View mArrowsView;

    private ArrayList<Coordinate> mSnakeTrail = new ArrayList<Coordinate>();
    private ArrayList<Coordinate> mAppleList = new ArrayList<Coordinate>();

    private static final Random RNG = new Random();
    
    private RefreshHandler mRedrawHandler = new RefreshHandler();

    class RefreshHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            SnakeView.this.update();
            SnakeView.this.invalidate();
        }

        public void sleep(long delayMillis) {
            this.removeMessages(0);
            sendMessageDelayed(obtainMessage(0), delayMillis);
        }
    };
    
    public SnakeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initSnakeView(context);
    }

    public SnakeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initSnakeView(context);
    }

    private void initSnakeView(Context context) {

        setFocusable(true);

        Resources r = this.getContext().getResources();

        resetTiles(5);
        loadTile(BODY, r.getDrawable(R.drawable.body));
        loadTile(HEAD, r.getDrawable(R.drawable.head));
        loadTile(APPLE, r.getDrawable(R.drawable.apple));
        loadTile(BORDER, r.getDrawable(R.drawable.border));

    }

    private void initNewGame() {
        mSnakeTrail.clear();
        mAppleList.clear();

        mSnakeTrail.add(new Coordinate(4, 7));
        mSnakeTrail.add(new Coordinate(3, 7));
        mSnakeTrail.add(new Coordinate(2, 7));
        mNextDirection = SOUTH;

        addRandomApple();

        mMoveDelay = 500;
        mScore = 0;
    }

    private int[] coordArrayListToArray(ArrayList<Coordinate> cvec) {
        int[] rawArray = new int[cvec.size() * 2];

        int i = 0;
        for (Coordinate c : cvec) {
            rawArray[i++] = c.x;
            rawArray[i++] = c.y;
        }

        return rawArray;
    }

    public Bundle saveState() {
        Bundle map = new Bundle();

        map.putIntArray("mAppleList", coordArrayListToArray(mAppleList));
        map.putInt("mDirection", Integer.valueOf(mDirection));
        map.putInt("mNextDirection", Integer.valueOf(mNextDirection));
        map.putInt("mMoveDelay", Integer.valueOf(mMoveDelay));
        map.putInt("mScore", Integer.valueOf(mScore));
        map.putIntArray("mSnakeTrail", coordArrayListToArray(mSnakeTrail));

        return map;
    }

    private ArrayList<Coordinate> coordArrayToArrayList(int[] rawArray) {
        ArrayList<Coordinate> coordArrayList = new ArrayList<Coordinate>();

        int coordCount = rawArray.length;
        for (int index = 0; index < coordCount; index += 2) {
            Coordinate c = new Coordinate(rawArray[index], rawArray[index + 1]);
            coordArrayList.add(c);
        }
        return coordArrayList;
    }

    public void restoreState(Bundle key) {
        setMode(PAUSE);

        mAppleList = coordArrayToArrayList(key.getIntArray("mAppleList"));
        mDirection = key.getInt("mDirection");
        mNextDirection = key.getInt("mNextDirection");
        mMoveDelay = key.getInt("mMoveDelay");
        mScore = key.getInt("mScore");
        mSnakeTrail = coordArrayToArrayList(key.getIntArray("mSnakeTrail"));
    }

    public void moveSnake(int direction) {

        if (direction == Snake.MOVE_UP) {
            if (mMode == READY | mMode == LOSE) {
                initNewGame();
                setMode(RUNNING);
                update();
                return;
            }

            if (mMode == PAUSE) {
                setMode(RUNNING);
                update();
                return;
            }

            if (mDirection != SOUTH) {
                mNextDirection = NORTH;
            }
            return;
        }

        if (direction == Snake.MOVE_DOWN) {
            if (mDirection != NORTH) {
                mNextDirection = SOUTH;
            }
            return;
        }

        if (direction == Snake.MOVE_LEFT) {
            if (mDirection != EAST) {
                mNextDirection = WEST;
            }
            return;
        }

        if (direction == Snake.MOVE_RIGHT) {
            if (mDirection != WEST) {
                mNextDirection = EAST;
            }
            return;
        }

    }

    public void setDependentViews(TextView msgView, View arrowView, View backgroundView) {
        mStatusText = msgView;
        mArrowsView = arrowView;
    }

    public void setMode(int newMode) {
        int oldMode = mMode;
        mMode = newMode;

        if (newMode == RUNNING && oldMode != RUNNING) {
            mStatusText.setVisibility(View.INVISIBLE);
            update();
            mArrowsView.setVisibility(View.VISIBLE);
            return;
        }

        Resources res = getContext().getResources();
        CharSequence str = "";
        if (newMode == PAUSE) {
            mArrowsView.setVisibility(View.VISIBLE);
            str = res.getText(R.string.mode_pause);
        }
        if (newMode == READY) {
            mArrowsView.setVisibility(View.VISIBLE);
            str = res.getText(R.string.mode_ready);
        }
        if (newMode == LOSE) {
            mArrowsView.setVisibility(View.VISIBLE);
            str = res.getString(R.string.mode_lose, mScore);
            ///
            
            
            
            ///
        }

        mStatusText.setText(str);
        mStatusText.setVisibility(View.VISIBLE);
    }

    public int getGameState() {
        return mMode;
    }

    private void addRandomApple() {
        Coordinate newCoord = null;
        boolean found = false;
        while (!found) {
            int newX = 1 + RNG.nextInt(mXTileCount - 2);
            int newY = 1 + RNG.nextInt(mYTileCount - 2);
            newCoord = new Coordinate(newX, newY);

            boolean collision = false;
            int snakelength = mSnakeTrail.size();
            for (int index = 0; index < snakelength; index++) {
                if (mSnakeTrail.get(index).equals(newCoord)) {
                    collision = true;
                }
            }
            found = !collision;
        }
        mAppleList.add(newCoord);
    }

    public void update() {
        if (mMode == RUNNING) {
            long now = System.currentTimeMillis();

            if (now - mLastMove > mMoveDelay) {
                clearTiles();
                updateWalls();
                updateSnake();
                updateApples();
                mLastMove = now;
            }
            mRedrawHandler.sleep(mMoveDelay);
        }

    }

    
    private void updateWalls() {
        for (int x = 0; x < mXTileCount; x++) {
            setTile(BORDER, x, 0);
            setTile(BORDER, x, mYTileCount - 1);
        }
        for (int y = 1; y < mYTileCount - 1; y++) {
            setTile(BORDER, 0, y);
            setTile(BORDER, mXTileCount - 1, y);
        }
    }
    

    private void updateApples() {
        for (Coordinate c : mAppleList) {
            setTile(APPLE, c.x, c.y);
        }
    }

    private void updateSnake() {
        boolean growSnake = false;

        Coordinate head = mSnakeTrail.get(0);
        Coordinate newHead = new Coordinate(1, 1);

        mDirection = mNextDirection;

        switch (mDirection) {
            case EAST: {
                newHead = new Coordinate(head.x + 1, head.y);
                break;
            }
            case WEST: {
                newHead = new Coordinate(head.x - 1, head.y);
                break;
            }
            case NORTH: {
                newHead = new Coordinate(head.x, head.y - 1);
                break;
            }
            case SOUTH: {
                newHead = new Coordinate(head.x, head.y + 1);
                break;
            }
        }

        if ((newHead.x < 1) || (newHead.y < 1) || (newHead.x > mXTileCount - 2) || (newHead.y > mYTileCount - 2)) {
            setMode(LOSE);
            return;

        }

        int snakelength = mSnakeTrail.size();
        for (int snakeindex = 0; snakeindex < snakelength; snakeindex++) {
            Coordinate c = mSnakeTrail.get(snakeindex);
            if (c.equals(newHead)) {
                setMode(LOSE);
                return;
            }
        }

        int applecount = mAppleList.size();
        for (int appleindex = 0; appleindex < applecount; appleindex++) {
            Coordinate c = mAppleList.get(appleindex);
            if (c.equals(newHead)) {
                mAppleList.remove(c);
                addRandomApple();

                mScore++;
                mMoveDelay *= 0.9;
                growSnake = true;
            }
        }

        mSnakeTrail.add(0, newHead);
        
        if (!growSnake) {
            mSnakeTrail.remove(mSnakeTrail.size() - 1);
        }

        int index = 0;
        for (Coordinate c : mSnakeTrail) {
            if (index == 0) {
                setTile(HEAD, c.x, c.y);
            } else {
                setTile(BODY, c.x, c.y);
            }
            index++;
        }

    }

    private class Coordinate {
        public int x;
        public int y;

        public Coordinate(int newX, int newY) {
            x = newX;
            y = newY;
        }

        public boolean equals(Coordinate other) {
            if (x == other.x && y == other.y) {
                return true;
            }
            return false;
        }
    } 
}
