package com.cr.response;

public class RankResponse {
    private Long rankId;
    private String rank;
    private byte[] rankImage;

    public Long getRankId() {
        return rankId;
    }

    public void setRankId(Long rankId) {
        this.rankId = rankId;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public byte[] getRankImage() {
        return rankImage;
    }

    public void setRankImage(byte[] rankImage) {
        this.rankImage = rankImage;
    }
}
