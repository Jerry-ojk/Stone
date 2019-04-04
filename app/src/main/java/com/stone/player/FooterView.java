package com.stone.player;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.stone.views.DPUtils;


public class FooterView extends View {
    private Paint paint;
    private float textOffset;
    private String currentTime;
    private String endTime;
    private boolean isTouchPlay = false;
    private boolean isTouchZoom = false;
    private boolean isTouchSeek = false;
    private PlayerController controller;
    private boolean isPlaying = true;
    private boolean isDragging = false;
    private boolean canDrag = true;
    private float lastX = 0;
    private float start;
    private float processLen;
    private float processOffset = 0;
    private final float strWidth;
    private long process = 0;

    private StringBuilder builder = new StringBuilder();
    private long duration;
    private float bufferLen;
    private int bufferPercent;

    public FooterView(Context context, PlayerController controller) {
        super(context);
        this.controller = controller;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(6);
        paint.setTextSize(DPUtils.DP12);
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        textOffset = (fontMetrics.bottom - fontMetrics.top - 6) / 2 - fontMetrics.descent;
        currentTime = "00:00";
        endTime = "00:00";
        strWidth = paint.measureText(currentTime, 0, currentTime.length());
        start = DPUtils.DP32 + strWidth;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        final int height2 = getHeight() >> 1;
        final int width = getWidth();
        paint.setColor(0xFFFFFFFF);
        //播放暂停按钮
        if (isPlaying) {
            paint.setStrokeWidth(DPUtils.DP4);
            canvas.drawLine(DPUtils.DP10, DPUtils.DP15, DPUtils.DP10, DPUtils.DP25, paint);
            canvas.drawLine(DPUtils.DP16, DPUtils.DP15, DPUtils.DP16, DPUtils.DP25, paint);
        } else {
            paint.setStrokeWidth(DPUtils.DP4);
            canvas.drawLine(DPUtils.DP9, DPUtils.DP15, DPUtils.DP9, DPUtils.DP25, paint);
            canvas.drawLine(DPUtils.DP9, DPUtils.DP15, DPUtils.DP16, DPUtils.DP20, paint);
            canvas.drawLine(DPUtils.DP9, DPUtils.DP25, DPUtils.DP16, DPUtils.DP20, paint);
        }
        //全屏按钮
        paint.setStrokeWidth(DPUtils.DP2);

        if (controller.isLandscape()) {
            final int end_front = width - DPUtils.DP15;
            final int end_end = width - DPUtils.DP8 - DPUtils.DP3;

            canvas.drawLine(end_front, DPUtils.DP18, end_front - DPUtils.DP3, DPUtils.DP18, paint);
            canvas.drawLine(end_front, DPUtils.DP18, end_front, DPUtils.DP15, paint);

            canvas.drawLine(end_front, DPUtils.DP22, end_front - DPUtils.DP3, DPUtils.DP22, paint);
            canvas.drawLine(end_front, DPUtils.DP22, end_front, DPUtils.DP25, paint);

            canvas.drawLine(end_end, DPUtils.DP18, end_end + DPUtils.DP3, DPUtils.DP18, paint);
            canvas.drawLine(end_end, DPUtils.DP18, end_end, DPUtils.DP15, paint);

            canvas.drawLine(end_end, DPUtils.DP22, end_end + DPUtils.DP3, DPUtils.DP22, paint);
            canvas.drawLine(end_end, DPUtils.DP22, end_end, DPUtils.DP25, paint);
        } else {
            final int end_front = width - DPUtils.DP18;
            final int end_end = width - DPUtils.DP8;
            canvas.drawLine(end_front, DPUtils.DP15, end_front + DPUtils.DP3, DPUtils.DP15, paint);
            canvas.drawLine(end_front, DPUtils.DP15, end_front, DPUtils.DP18, paint);

            canvas.drawLine(end_front, DPUtils.DP25, end_front + DPUtils.DP3, DPUtils.DP25, paint);
            canvas.drawLine(end_front, DPUtils.DP25, end_front, DPUtils.DP22, paint);

            canvas.drawLine(end_end, DPUtils.DP15, end_end - DPUtils.DP3, DPUtils.DP15, paint);
            canvas.drawLine(end_end, DPUtils.DP15, end_end, DPUtils.DP18, paint);

            canvas.drawLine(end_end, DPUtils.DP25, end_end - DPUtils.DP3, DPUtils.DP25, paint);
            canvas.drawLine(end_end, DPUtils.DP25, end_end, DPUtils.DP22, paint);
        }
        //开始时间停止时间
        canvas.drawText(currentTime, DPUtils.DP24, height2 + textOffset, paint);
        canvas.drawText(endTime, width - DPUtils.DP24 - strWidth, height2 + textOffset, paint);
        final float front = processOffset + start;

        //没播放的进度条
        paint.setStrokeWidth(8);
        paint.setColor(0xFF6b6666);
        canvas.drawLine(front, height2, width - start, height2, paint);
        //缓冲进度条
        if (bufferLen > processOffset) {
            paint.setColor(0xFFC3BEBE);
            canvas.drawLine(front, height2, bufferLen + start, height2, paint);
        }
        //已经播放的进度条
        paint.setColor(0xFFFF584C);
        canvas.drawLine(start, height2, front, height2, paint);

        //进度条锚点
        if (isTouchSeek) {
            paint.setColor(0x5FFFFFFF);
            canvas.drawCircle(front, height2, DPUtils.DP16, paint);
        }
        paint.setColor(0x7FFFFFFF);
        canvas.drawCircle(front, height2, DPUtils.DP9, paint);
        paint.setColor(0xFFFFFFFF);
        canvas.drawCircle(front, height2, DPUtils.DP6, paint);
        paint.setColor(0xFFFF584C);
        canvas.drawCircle(front, height2, DPUtils.DP3, paint);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        processLen = getWidth() - start * 2;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float x = event.getX();
                if (x < DPUtils.DP32) {
                    isTouchPlay = true;
                    isTouchZoom = false;
                    isTouchSeek = false;
                } else if (x > getWidth() - DPUtils.DP32) {
                    isTouchPlay = false;
                    isTouchZoom = true;
                    isTouchSeek = false;
                } else if (x > DPUtils.DP48 && x < getWidth() - DPUtils.DP48) {
                    isTouchPlay = false;
                    isTouchZoom = false;
                    isTouchSeek = true;
                    updateProcess();
                } else {
                    isTouchPlay = false;
                    isTouchZoom = false;
                    isTouchSeek = false;
                }
                lastX = x;
                return true;
            case MotionEvent.ACTION_MOVE:
                x = event.getX();
                if (isDragging) {
                    final float offset = x - lastX;
                    //Log.i("666", "x=" + x + ",lastX=" + lastX);
                    dealOffset(offset);
                } else if (Math.abs(x - lastX) > DPUtils.DP4) {
                    if (isTouchPlay) {
                        isTouchPlay = false;
                    } else if (isTouchZoom) {
                        isTouchZoom = false;
                    } else if (isTouchSeek) {
                        isDragging = true;
                    }
                }
                lastX = x;
                return true;
            case MotionEvent.ACTION_UP:
                //Log.i("666", "isTouchPlay=" + isTouchPlay + ",isTouchZoom=" + isTouchZoom + ",isTouchSeek=" + isTouchSeek + ",isDragging=" + isDragging);
                if (isTouchSeek) {
                    isTouchSeek = false;
                    if (isDragging) {
                        isDragging = false;
                        dealOffset(event.getX() - lastX);
                        controller.onDrag(process);
                    }
                    updateProcess();
                } else if (isTouchPlay) {
                    if (controller.onPlay(isPlaying)) {
                        isPlaying = !isPlaying;
                        invalidate();
                    }
                } else if (isTouchZoom) {
                    controller.onZoom();
                }
                return true;
            case MotionEvent.ACTION_CANCEL:
                isTouchPlay = false;
                isTouchZoom = false;
                isTouchSeek = false;
                isDragging = false;
                updateProcess();
                return true;
        }
        return true;
    }

    public void updateProcess() {
        invalidate();
    }

    public void updateOffset(float Offset) {
        processOffset = Offset;
        final float rate = (Offset / processLen);
        process = (long) (duration * rate);
        currentTime = time(process);
        updateProcess();
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
        endTime = time(duration);
        invalidate();
    }

    public void showPause() {
        isPlaying = !isPlaying;
        invalidate();
    }

    private void dealOffset(float offset) {
        //Log.i("dealOffset", "offset=" + offset);
        final float tmp = offset + processOffset;
        if (tmp <= 0) {
            if (processOffset != 0) {
                process = 0;
                currentTime = "00:00";
                processOffset = 0;
                updateProcess();
            }
            //Log.i("dealOffset", "offset<0," + processOffset + "");
        } else if (tmp >= processLen) {
            if (processOffset != processLen) {
                process = duration;
                currentTime = time(duration);
                processOffset = processLen;
                updateProcess();
            }
            //Log.i("dealOffset", "offset>max," + processOffset + "");
        } else {
            updateOffset(tmp);
            //Log.i("dealOffset", "offset~max," + processOffset + "");
        }
    }

    private String time(long time) {
        builder.setLength(0);
        time = time / 1000;
        final long m = time / 60;
        if (m < 10) {
            builder.append('0');
        }
        builder.append(m);
        builder.append(':');
        final long s = time - 60 * m;
        if (s < 10) {
            builder.append('0');
        }
        builder.append(s);
        return builder.toString();
    }

    public long getPlayPosition() {
        return process;
    }

    public void setPlayPosition(long position) {
        if (isDragging) return;
        if (process != position) {
            if (position >= duration - 2) {
                process = duration;
                currentTime = time(duration);
                processOffset = processLen;
                showPause();
            } else {
                process = position;
                processOffset = ((float) position / (float) duration) * processLen;
                currentTime = time(process);
                updateProcess();
            }
        }
    }

    public void setBufferPercent(int percent) {
        if (bufferPercent != percent) {
            bufferPercent = percent;
            bufferLen = (percent / 100f) * processLen;
            if (bufferLen > processOffset) {
                updateProcess();
            }
        } else if (bufferPercent > 95) {
            bufferPercent = 100;
            bufferLen = processLen;
            if (bufferLen > processOffset) {
                updateProcess();
            }
        }
    }

    public void setBufferLengthPixel(float bufferLength) {
        if (bufferPercent != bufferLength) {
            if (bufferLen > processOffset) {
                updateProcess();
            }
        }
    }

    public float getBufferLengthPixel() {
        return bufferLen;
    }
}