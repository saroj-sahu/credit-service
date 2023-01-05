package com.cr.entity;

import javax.persistence.*;
import java.io.File;
import java.sql.Blob;

@Table(name = "RANK_T")
@Entity
public class Rank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "rank_id", nullable = false)
    private String rankId;

    @Column(name = "rank_name", nullable = false)
    private String rankName;

    @Column(name = "rank_img", nullable = true)
    private Blob rankImage;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRankId() {
        return rankId;
    }

    public void setRankId(String rankId) {
        this.rankId = rankId;
    }

    public String getRankName() {
        return rankName;
    }

    public void setRankName(String rankName) {
        this.rankName = rankName;
    }

    public Blob getRankImage() {
        return rankImage;
    }

    public void setRankImage(Blob rankImage) {
        this.rankImage = rankImage;
    }
}
