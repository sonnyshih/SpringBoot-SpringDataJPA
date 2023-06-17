package com.example.core.util;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * refer to: https://www.cnblogs.com/ming-blogs/p/16271358.html
 * */
public class CaptchaUtil {

    /**
     *  預設驗證碼字集
     */
    private final char[] CHARS;
    /**
     * 預設字符数量
     */
    private final Integer SIZE;
    /**
     * 預設干擾線數量
     */
    private final int LINES;
    /**
     * 預設圖片宽度
     */
    private final int WIDTH;
    /**
     * 預設圖片高度
     */
    private final int HEIGHT;
    /**
     * 預設字體大小
     */
    private final int FONT_SIZE;
    /**
     * 預設斜體字
     */
    private final boolean TILT;

    private final int THETA_DEGREE;

    /**
     * 背景顏色
     */
    private final Color BACKGROUND_COLOR;

    /**
     * 初始化基础参数
     *
     * @param builder
     */
    private CaptchaUtil(Builder builder) {
        CHARS = builder.chars;
        SIZE = builder.size;
        LINES = builder.lines;
        WIDTH = builder.width;
        HEIGHT = builder.height;
        FONT_SIZE = builder.fontSize;
        TILT = builder.tilt;
        THETA_DEGREE = builder.thetaDegree;
        BACKGROUND_COLOR = builder.backgroundColor;
    }

    /**
     * 实例化构造器对象
     *
     * @return
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * @return 產生驗證碼與圖片
     * Object[0]：驗證碼字串
     * Object[1]：驗證碼圖片。
     */
    public Object[] createImage() {
        StringBuffer sb = new StringBuffer();
        // 建立空白圖片
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

        // 取得圖片畫筆
        Graphics2D graphic = image.createGraphics();

        //
        graphic.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 設置畫筆顏色
        graphic.setColor(BACKGROUND_COLOR);

        // 繪製矩形背景
        graphic.fillRect(0, 0, WIDTH, HEIGHT);

        Random ran = new Random();

        //graphic.setBackground(Color.WHITE);

        // 計算每個字符佔的寬度，這裡預留一個字符的位置用於左右邊距
        int codeWidth = WIDTH / (SIZE + 1);

        // 字符所處的y軸的坐標
        int y = HEIGHT * 3 / 4;

        for (int i = 0; i < SIZE; i++) {
            // 設置隨機顏色
            graphic.setColor(getRandomColor());

            // 初始化字體
            Font font = new Font(null, Font.BOLD + Font.ITALIC, FONT_SIZE);

            if (TILT) {
                // 隨機傾斜角度 (-30到30度之間)
                int theta = ran.nextInt(THETA_DEGREE);

                // 隨機傾斜方向 左或者右
                theta = (ran.nextBoolean() == true) ? theta : -theta;
                AffineTransform affineTransform = new AffineTransform();
                affineTransform.rotate(Math.toRadians(theta), 0, 0);
                font = font.deriveFont(affineTransform);
            }

            // 設定字形、大小
            graphic.setFont(font);

            // 計算當前字符繪製的X軸坐標
            int x = (i * codeWidth) + (codeWidth / 2);

            // 隨機取字索引
            int n = ran.nextInt(CHARS.length);

            // 得到字
            String code = String.valueOf(CHARS[n]);

            // 畫字到圖中
            graphic.drawString(code, x, y);

            // 記錄字
            sb.append(code);
        }

        // 畫干擾線
        for (int i = 0; i < LINES; i++) {
            // 設定隨機顏色
            graphic.setColor(getRandomColor());

            // 隨機畫線
            graphic.drawLine(ran.nextInt(WIDTH), ran.nextInt(HEIGHT), ran.nextInt(WIDTH), ran.nextInt(HEIGHT));
        }

        // 返回驗證碼和圖片
        return new Object[]{sb.toString(), image};
    }

    /**
     * 隨機取色
     */
    private Color getRandomColor() {
        Random ran = new Random();
        Color color = new Color(ran.nextInt(256), ran.nextInt(256), ran.nextInt(256));
        return color;
    }

    /**
     * 构造器对象
     */
    public static class Builder {
        // 預設驗證碼字集
        private char[] chars = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        // 預設字符数量
        private int size = 4;
        // 預設干擾線數量
        private int lines = 10;
        // 預設圖片宽度
        private int width = 80;
        // 預設圖片高度
        private int height = 35;
        // 預設字體大小
        private int fontSize = 25;
        // 預設斜體字
        private boolean tilt = true;
        private int thetaDegree = 30;
        //背景顏色
        private Color backgroundColor = Color.LIGHT_GRAY;

        public Builder setChars(char[] chars) {
            this.chars = chars;
            return this;
        }

        public Builder setSize(int size) {
            this.size = size;
            return this;
        }

        public Builder setLines(int lines) {
            this.lines = lines;
            return this;
        }

        public Builder setWidth(int width) {
            this.width = width;
            return this;
        }

        public Builder setHeight(int height) {
            this.height = height;
            return this;
        }

        public Builder setFontSize(int fontSize) {
            this.fontSize = fontSize;
            return this;
        }

        public Builder setTilt(boolean tilt) {
            this.tilt = tilt;
            return this;
        }

        public Builder setThetaDegree(int thetaDegree) {
            this.thetaDegree = thetaDegree;
            return this;
        }

        public Builder setBackgroundColor(Color backgroundColor) {
            this.backgroundColor = backgroundColor;
            return this;
        }

        public CaptchaUtil build() {
            return new CaptchaUtil(this);
        }
    }

}
