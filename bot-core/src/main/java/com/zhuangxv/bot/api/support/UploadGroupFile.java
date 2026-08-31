/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.alibaba.fastjson2.annotation.JSONField
 */
package com.zhuangxv.bot.api.support;

import com.alibaba.fastjson2.annotation.JSONField;
import com.zhuangxv.bot.api.BaseApi;

public class UploadGroupFile
extends BaseApi {
    private final Param param = new Param();

    public UploadGroupFile(long groupId, String file, String name, String folder) {
        this.param.setGroupId(groupId);
        this.param.setFile(file);
        this.param.setName(name);
        this.param.setFolder(folder);
    }

    @Override
    public boolean needSleep() {
        return true;
    }

    @Override
    public String getAction() {
        return "upload_group_file";
    }

    @Override
    public Object getParams() {
        return this.param;
    }

    public static class Param {
        @JSONField(name="group_id")
        private long groupId;
        @JSONField(name="file")
        private String file;
        @JSONField(name="name")
        private String name;
        @JSONField(name="folder")
        private String folder;

        public long getGroupId() {
            return this.groupId;
        }

        public String getFile() {
            return this.file;
        }

        public String getName() {
            return this.name;
        }

        public String getFolder() {
            return this.folder;
        }

        public void setGroupId(long groupId) {
            this.groupId = groupId;
        }

        public void setFile(String file) {
            this.file = file;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setFolder(String folder) {
            this.folder = folder;
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof Param)) {
                return false;
            }
            Param other = (Param)o;
            if (!other.canEqual(this)) {
                return false;
            }
            if (this.getGroupId() != other.getGroupId()) {
                return false;
            }
            String this$file = this.getFile();
            String other$file = other.getFile();
            if (this$file == null ? other$file != null : !this$file.equals(other$file)) {
                return false;
            }
            String this$name = this.getName();
            String other$name = other.getName();
            if (this$name == null ? other$name != null : !this$name.equals(other$name)) {
                return false;
            }
            String this$folder = this.getFolder();
            String other$folder = other.getFolder();
            return !(this$folder == null ? other$folder != null : !this$folder.equals(other$folder));
        }

        protected boolean canEqual(Object other) {
            return other instanceof Param;
        }

        public int hashCode() {
            int PRIME = 59;
            int result = 1;
            long $groupId = this.getGroupId();
            result = result * 59 + (int)($groupId >>> 32 ^ $groupId);
            String $file = this.getFile();
            result = result * 59 + ($file == null ? 43 : $file.hashCode());
            String $name = this.getName();
            result = result * 59 + ($name == null ? 43 : $name.hashCode());
            String $folder = this.getFolder();
            result = result * 59 + ($folder == null ? 43 : $folder.hashCode());
            return result;
        }

        public String toString() {
            return "UploadGroupFile.Param(groupId=" + this.getGroupId() + ", file=" + this.getFile() + ", name=" + this.getName() + ", folder=" + this.getFolder() + ")";
        }

        private Param() {
        }
    }
}

