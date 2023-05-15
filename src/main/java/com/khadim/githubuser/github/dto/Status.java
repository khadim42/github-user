package com.khadim.githubuser.github.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Status {

    @SerializedName("status")
    @Expose
    private String status;

    public Status(String stat){
        this.status = stat;
    }

}
