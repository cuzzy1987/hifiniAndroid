package com.me.hifimusic.widget

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.annotation.TargetApi
import android.view.View
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.os.Build
import android.os.Handler
import android.util.AttributeSet
import android.util.Log
import android.view.ViewGroup
import android.view.MotionEvent
import com.me.hifimusic.R


class DashboardView : View {

    var mCurrentText: String = ""
    var mNextText: String = ""

    // 文字宽度
    var mTextMaxWidth: Int = 0
    // 文字左侧padding值
    var mPaddingLeft: Int = 0
    // 文字高度
    var mTextHeight: Int = 0
    // 文字顶部padding值
    var mPaddingTop: Int = 0
    // 文字底部padding值
    var mPaddingBottom: Int = 0
    // 文本动态移动的距离
    private var mCurrentTextMoveMarginTop: Int = 0
    // 下一个文本动态移动的距离
    var mNextTextMoveMarginTop: Int = 0

    var mCurrentTextPosition: Int = 0
    var mNextTextPosition: Int = 0

    // 初始化当前文本到顶部的距离
    var mCurrentTextInitMarginTop: Int = 0
    // 初始化下一个文本到顶部的距离
    var mNextTextInitMarginTop: Int = 0
    // 文本需要移动的距离
    var mTextMoveOffset: Int = 0

    var mProgress: Float = 0f
    var valueAnimator: ValueAnimator = ValueAnimator()


    var mTextColor: Int = 0
    var mTextSize: Int = 0

    // 动画是否暂停
    var mIsPause: Boolean = false
    // 重复延迟的时间间隔(毫秒单位)
    var reRepeatDelayTime: Int = 0
    // 动画持续时间
    var itemAnimationTime: Int = 0
    // 延迟时间
    var startDelayTime: Int = 0


    lateinit var mPaint: Paint

    var mTextArray = arrayOf<String?>()

    private lateinit var onItemClickListener: OnItemClickListener


    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    public fun setOnItemClickListener(onItemClickListener: OnItemClickListener){
        this.onItemClickListener = onItemClickListener
    }

    constructor(context: Context?) :super(context)


    constructor(context: Context, attrs: AttributeSet) :super(context,attrs){
        init(context,attrs)
    }


    private fun init(context: Context, attrs: AttributeSet){
        var typedArray: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.MarqueeViewStyle)

        mTextColor = typedArray.getColor(R.styleable.MarqueeViewStyle_textColor, Color.BLACK)
        mTextSize = typedArray.getDimensionPixelSize(R.styleable.MarqueeViewStyle_textSize, 45)

        mPaddingLeft = typedArray.getDimensionPixelSize(R.styleable.MarqueeViewStyle_paddingLeft, 15)
        mPaddingTop = typedArray.getDimensionPixelSize(R.styleable.MarqueeViewStyle_paddingTopBottom, 25)
        mPaddingBottom = typedArray.getDimensionPixelSize(R.styleable.MarqueeViewStyle_paddingTopBottom, 25)
        mPaddingTop = typedArray.getDimensionPixelSize(R.styleable.MarqueeViewStyle_paddingTop, mPaddingTop)
        mPaddingBottom = typedArray.getDimensionPixelSize(R.styleable.MarqueeViewStyle_paddingBottom, mPaddingBottom)

        itemAnimationTime = typedArray.getInteger(R.styleable.MarqueeViewStyle_itemAnimationTime, 1000)
        reRepeatDelayTime = typedArray.getInteger(R.styleable.MarqueeViewStyle_reRepeatDelayTime, 1000)
        startDelayTime = typedArray.getInteger(R.styleable.MarqueeViewStyle_startDelayTime, 500)

        typedArray.recycle()

        mPaint = Paint()
        mPaint.isAntiAlias = true
        mPaint.textSize = mTextSize.toFloat()
        mPaint.color = mTextColor
        mPaint.textAlign = Paint.Align.LEFT

    }

    fun setTextArray(arr: Array<String?>){
        if (arr.isNotEmpty()){
            mTextArray = arr
            //初始化文字区域
            initTextRect()
            //初始化将要显示的文字
            setTextCurrentOrNextStatus(0,1,true)
            //动画效果
            startAnimation()
        }
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event?.action){
            MotionEvent.ACTION_UP -> if (this::onItemClickListener.isInitialized){
                onItemClickListener.onItemClick(mCurrentTextPosition)
                pause()
            }
        }
        return true
    }


    private fun setTextCurrentOrNextStatus(currentPosition: Int, nextPosition: Int, init: Boolean) {
        Log.e("setTextCurrentOrN_","curP${currentPosition}__nextP${nextPosition}__b$init")

        var transNextPosition =
            if(nextPosition >= mTextArray.size){ 0 }
            else{ nextPosition }

        mCurrentTextPosition = currentPosition
        mNextTextPosition = transNextPosition

        mCurrentText = mTextArray[mCurrentTextPosition].toString()
        mNextText = mTextArray[mNextTextPosition].toString()

        if (!init){
            return
        }else{
            viewTreeObserver.addOnGlobalLayoutListener {
                //mCurrentTextInitMarginTop = mCurrentTextMoveMarginTop = getFontBaseLine()
                val currentMargin = getFontBaseLine()
                mCurrentTextInitMarginTop  = currentMargin
                mCurrentTextMoveMarginTop  = currentMargin

                val nextMargin = measuredHeight + mTextHeight
                mNextTextInitMarginTop = nextMargin
                mNextTextMoveMarginTop = nextMargin

                mTextMoveOffset = mNextTextInitMarginTop - mCurrentTextInitMarginTop
            }
        }
    }

    private fun getFontBaseLine(): Int {
        var fontMetricsInt: Paint.FontMetricsInt = mPaint.fontMetricsInt
        return measuredHeight / 2 + (fontMetricsInt.descent - fontMetricsInt.ascent) / 2 - fontMetricsInt.descent
    }

    private fun initTextRect() {
        var size = mTextArray.size
        loop@ for (i in 0 until size){
            var text: String = mTextArray[i].toString()
            var rect: Rect = Rect()
            mPaint.getTextBounds(text,0,text.length,rect)
            if (mTextHeight == 0){
                mTextHeight = rect.height()
            }
            if (rect.width() > mTextMaxWidth){
                mTextMaxWidth = rect.width()
            }
        }
    }

    private fun startAnimation(){
        Log.e("startAnimation","-----------")
        val handler = Handler()
        valueAnimator = ValueAnimator.ofFloat(0f,1f)

        valueAnimator.addUpdateListener {
            mProgress = valueAnimator.animatedValue as Float
            val moveOffset: Int = (mTextMoveOffset * mProgress).toInt()
            mCurrentTextMoveMarginTop = mCurrentTextInitMarginTop - moveOffset
            mNextTextMoveMarginTop = mNextTextInitMarginTop - moveOffset
            postInvalidate()
        }
        valueAnimator.addListener(object: AnimatorListenerAdapter(){
            override fun onAnimationRepeat(animation: Animator?) {
//                super.onAnimationRepeat(animation)
                valueAnimator.pause()
                setTextCurrentOrNextStatus(mNextTextPosition,mNextTextPosition+1,false)
                handler.postDelayed({
                    if (!mIsPause){
                        valueAnimator.resume() }
                },reRepeatDelayTime.toLong())
            }
        })

        valueAnimator.repeatCount = -1
        valueAnimator.duration = itemAnimationTime.toLong()
        valueAnimator.startDelay = startDelayTime.toLong()
        valueAnimator.start()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (mTextArray.isEmpty()) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }else {
            val width = MeasureSpec.getSize(widthMeasureSpec)
            val height = MeasureSpec.getSize(heightMeasureSpec)
            val lp: ViewGroup.LayoutParams = layoutParams
            setMeasuredDimension(getViewWidth(lp, width), getViewHeight(lp, height))
        }
    }

    private fun getViewWidth(lp: ViewGroup.LayoutParams, pWidth: Int): Int {
        var width = 0
        when {
            lp.width >= 0 -> width = lp.width
            lp.width == ViewGroup.LayoutParams.WRAP_CONTENT -> width = mTextMaxWidth + mPaddingLeft
            lp.width == ViewGroup.LayoutParams.MATCH_PARENT -> width = pWidth
        }
        return width
    }


    private fun getViewHeight(lp: ViewGroup.LayoutParams,pHeight: Int): Int{
        var height = 0
        when{
            lp.height >= 0 -> height = lp.height
            lp.height == ViewGroup.LayoutParams.WRAP_CONTENT -> height = mTextHeight + mPaddingTop + mPaddingBottom
            lp.height == ViewGroup.LayoutParams.MATCH_PARENT -> height = pHeight
        }
        return height
    }


    override fun onDraw(canvas: Canvas?) {
        if (mTextArray.isEmpty()){
            super.onDraw(canvas)
        }else{
            canvas?.drawText(mCurrentText,mPaddingLeft.toFloat(),mCurrentTextMoveMarginTop.toFloat(),mPaint)
            canvas?.drawText(mNextText,mPaddingLeft.toFloat(),mNextTextMoveMarginTop.toFloat(),mPaint)
        }
    }


    fun destory() {
//        if (valueAnimator != null) {
//            valueAnimator.cancel()
//        }
        valueAnimator.cancel()
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private fun pause() {
        mIsPause = true
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    fun resume() {
        if (valueAnimator.isPaused) {
            valueAnimator.resume()
            mIsPause = false
        }
    }



}