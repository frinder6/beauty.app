package com.beauty.hb.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by frinder_liu on 2016/2/26.
 */
@Entity
@Table(name = "beauty_schema_columns", schema = "", catalog = "beauty")
public class BeautySchemaColumnsEntity {
    private long id;
    private String data;
    private String columnName;
    private String title;
    private String remark;
    private Timestamp createTime;
    private String creater;
    private Timestamp lastUpdateTime;
    private String modifier;
    private byte isDeleted;
    private String def1;
    private String def2;
    private Timestamp def3;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "data")
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Basic
    @Column(name = "column_name")
    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Basic
    @Column(name = "create_time")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "creater")
    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    @Basic
    @Column(name = "last_update_time")
    public Timestamp getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Timestamp lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    @Basic
    @Column(name = "modifier")
    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    @Basic
    @Column(name = "is_deleted")
    public byte getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(byte isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Basic
    @Column(name = "def1")
    public String getDef1() {
        return def1;
    }

    public void setDef1(String def1) {
        this.def1 = def1;
    }

    @Basic
    @Column(name = "def2")
    public String getDef2() {
        return def2;
    }

    public void setDef2(String def2) {
        this.def2 = def2;
    }

    @Basic
    @Column(name = "def3")
    public Timestamp getDef3() {
        return def3;
    }

    public void setDef3(Timestamp def3) {
        this.def3 = def3;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BeautySchemaColumnsEntity that = (BeautySchemaColumnsEntity) o;

        if (id != that.id) return false;
        if (isDeleted != that.isDeleted) return false;
        if (columnName != null ? !columnName.equals(that.columnName) : that.columnName != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (creater != null ? !creater.equals(that.creater) : that.creater != null) return false;
        if (data != null ? !data.equals(that.data) : that.data != null) return false;
        if (def1 != null ? !def1.equals(that.def1) : that.def1 != null) return false;
        if (def2 != null ? !def2.equals(that.def2) : that.def2 != null) return false;
        if (def3 != null ? !def3.equals(that.def3) : that.def3 != null) return false;
        if (lastUpdateTime != null ? !lastUpdateTime.equals(that.lastUpdateTime) : that.lastUpdateTime != null)
            return false;
        if (modifier != null ? !modifier.equals(that.modifier) : that.modifier != null) return false;
        if (remark != null ? !remark.equals(that.remark) : that.remark != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (data != null ? data.hashCode() : 0);
        result = 31 * result + (columnName != null ? columnName.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (creater != null ? creater.hashCode() : 0);
        result = 31 * result + (lastUpdateTime != null ? lastUpdateTime.hashCode() : 0);
        result = 31 * result + (modifier != null ? modifier.hashCode() : 0);
        result = 31 * result + (int) isDeleted;
        result = 31 * result + (def1 != null ? def1.hashCode() : 0);
        result = 31 * result + (def2 != null ? def2.hashCode() : 0);
        result = 31 * result + (def3 != null ? def3.hashCode() : 0);
        return result;
    }
}
