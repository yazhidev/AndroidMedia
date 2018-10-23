package com.yazhidev.androidmedia.utils.bean;

/**
 * Created by zengyazhi on 2018/10/23.
 */

public class WaveHeader {
    private String riff; // "RIFF"
    private int totalLength; //音频 data 数据长度 + 44 -8
    private String wave; // "WAVE"
    private String fmt; // "fmt "
    private int transition = 0x00000010;
    private short type; // PCM：1
    private short channelMask; // 单声道：1，双声道：2
    private int sampleRate; //采样率
    private int rate;  // 波形音频数据传送速率，其值为通道数×每秒数据位数×每样本的数据位数／8
    private short sampleLength; // 每个采样需要的字节数，其值为通道数×位深度／8
    private short deepness; //位深度
    private String data; // "data"
    private int dataLength; //data数据长度

    private WaveHeader(String riff, int totalLength, String wave, String fmt, int transition, short type, short channelMask, int sampleRate, int rate, short sampleLength, short deepness, String data, int dataLength) {
        this.riff = riff;
        this.totalLength = totalLength;
        this.wave = wave;
        this.fmt = fmt;
        this.transition = transition;
        this.type = type;
        this.channelMask = channelMask;
        this.sampleRate = sampleRate;
        this.rate = rate;
        this.sampleLength = sampleLength;
        this.deepness = deepness;
        this.data = data;
        this.dataLength = dataLength;
    }

    public String getRiff() {
        return riff;
    }

    public int getTotalLength() {
        return totalLength;
    }

    public String getWave() {
        return wave;
    }

    public String getFmt() {
        return fmt;
    }

    public int getTransition() {
        return transition;
    }

    public short getType() {
        return type;
    }

    public short getChannelMask() {
        return channelMask;
    }

    public int getSampleRate() {
        return sampleRate;
    }

    public int getRate() {
        return rate;
    }

    public short getSampleLength() {
        return sampleLength;
    }

    public short getDeepness() {
        return deepness;
    }

    public String getData() {
        return data;
    }

    public int getDataLength() {
        return dataLength;
    }

    public static class Builder {
        private String riff; // "RIFF"
        private int totalLength; //音频 data 数据长度 + 44 -8
        private String wave; // "WAVE"
        private String fmt; // "fmt "
        private int transition = 0x00000010;
        private short type; // PCM：1
        private short channelMask; // 单声道：1，双声道：2
        private int sampleRate; //采样率
        private int rate;  // 波形音频数据传送速率，其值为通道数×每秒数据位数×每样本的数据位数／8
        private short sampleLength; // 每个采样需要的字节数，其值为通道数×位深度／8
        private short deepness; //位深度
        private String data; // "data"
        private int dataLength; //data数据长度

        public Builder setRiff(String riff) {
            this.riff = riff;
            return this;
        }

        public Builder setTotalLength(int totalLength) {
            this.totalLength = totalLength;
            return this;
        }

        public Builder setWave(String wave) {
            this.wave = wave;
            return this;
        }

        public Builder setFmt(String fmt) {
            this.fmt = fmt;
            return this;
        }

        public Builder setTransition(int transition) {
            this.transition = transition;
            return this;
        }

        public Builder setType(short type) {
            this.type = type;
            return this;
        }

        public Builder setChannelMask(short channelMask) {
            this.channelMask = channelMask;
            return this;
        }

        public Builder setSampleRate(int sampleRate) {
            this.sampleRate = sampleRate;
            return this;
        }

        public Builder setRate(int rate) {
            this.rate = rate;
            return this;
        }

        public Builder setSampleLength(short sampleLength) {
            this.sampleLength = sampleLength;
            return this;
        }

        public Builder setDeepness(short deepness) {
            this.deepness = deepness;
            return this;
        }

        public Builder setData(String data) {
            this.data = data;
            return this;
        }

        public Builder setDataLength(int dataLength) {
            this.dataLength = dataLength;
            return this;
        }

        public WaveHeader build() {
            return new WaveHeader(riff, totalLength, wave, fmt, transition, type, channelMask, sampleRate, rate, sampleLength, deepness, data, dataLength);
        }
    }
}
