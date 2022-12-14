package com.program.moudle_base.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CollectionBean implements Serializable {

    /**
     * success : true
     * code : 10000
     * message : 查询成功.
     * data : {"content":[{"_id":"1019644039198146560","userId":"1499922423573647361","userName":null,"avatar":null,"cover":"https://images.sunofbeaches.com/content/2022_09_14/1019643983925608448.png","name":"api","permission":"1","description":"test","createTime":"2022-09-14 16:21","followCount":0,"favoriteCount":1}],"pageable":{"sort":{"sorted":true,"unsorted":false},"pageSize":10,"pageNumber":0,"offset":0,"paged":true,"unpaged":false},"totalPages":1,"totalElements":1,"last":true,"number":0,"size":10,"numberOfElements":1,"sort":{"sorted":true,"unsorted":false},"first":true}
     */

    @SerializedName("success")
    private Boolean success;
    @SerializedName("code")
    private Integer code;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private DataBean data;

    public Boolean getSuccess() {
        return success;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public DataBean getData() {
        return data;
    }

    @Override
    public String toString() {
        return "CollectionBean{" +
                "success=" + success +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }


    public static class DataBean implements Serializable {
        /**
         * content : [{"_id":"1019644039198146560","userId":"1499922423573647361","userName":null,"avatar":null,"cover":"https://images.sunofbeaches.com/content/2022_09_14/1019643983925608448.png","name":"api","permission":"1","description":"test","createTime":"2022-09-14 16:21","followCount":0,"favoriteCount":1}]
         * pageable : {"sort":{"sorted":true,"unsorted":false},"pageSize":10,"pageNumber":0,"offset":0,"paged":true,"unpaged":false}
         * totalPages : 1
         * totalElements : 1
         * last : true
         * number : 0
         * size : 10
         * numberOfElements : 1
         * sort : {"sorted":true,"unsorted":false}
         * first : true
         */

        @SerializedName("pageable")
        private PageableBean pageable;
        @SerializedName("totalPages")
        private Integer totalPages;
        @SerializedName("totalElements")
        private Integer totalElements;
        @SerializedName("last")
        private Boolean last;
        @SerializedName("number")
        private Integer number;
        @SerializedName("size")
        private Integer size;
        @SerializedName("numberOfElements")
        private Integer numberOfElements;
        @SerializedName("sort")
        private SortBeanX sort;
        @SerializedName("first")
        private Boolean first;
        @SerializedName("content")
        private List<ContentBean> content;

        public PageableBean getPageable() {
            return pageable;
        }

        public Integer getTotalPages() {
            return totalPages;
        }

        public Integer getTotalElements() {
            return totalElements;
        }

        public Boolean getLast() {
            return last;
        }

        public Integer getNumber() {
            return number;
        }

        public Integer getSize() {
            return size;
        }

        public Integer getNumberOfElements() {
            return numberOfElements;
        }

        public SortBeanX getSort() {
            return sort;
        }

        public Boolean getFirst() {
            return first;
        }

        public List<ContentBean> getContent() {
            return content;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "pageable=" + pageable +
                    ", totalPages=" + totalPages +
                    ", totalElements=" + totalElements +
                    ", last=" + last +
                    ", number=" + number +
                    ", size=" + size +
                    ", numberOfElements=" + numberOfElements +
                    ", sort=" + sort +
                    ", first=" + first +
                    ", content=" + content +
                    '}';
        }

        public static class PageableBean implements Serializable {
            /**
             * sort : {"sorted":true,"unsorted":false}
             * pageSize : 10
             * pageNumber : 0
             * offset : 0
             * paged : true
             * unpaged : false
             */

            @SerializedName("sort")
            private SortBean sort;
            @SerializedName("pageSize")
            private Integer pageSize;
            @SerializedName("pageNumber")
            private Integer pageNumber;
            @SerializedName("offset")
            private Integer offset;
            @SerializedName("paged")
            private Boolean paged;
            @SerializedName("unpaged")
            private Boolean unpaged;

            public SortBean getSort() {
                return sort;
            }

            public Integer getPageSize() {
                return pageSize;
            }

            public Integer getPageNumber() {
                return pageNumber;
            }

            public Integer getOffset() {
                return offset;
            }

            public Boolean getPaged() {
                return paged;
            }

            public Boolean getUnpaged() {
                return unpaged;
            }

            @Override
            public String toString() {
                return "PageableBean{" +
                        "sort=" + sort +
                        ", pageSize=" + pageSize +
                        ", pageNumber=" + pageNumber +
                        ", offset=" + offset +
                        ", paged=" + paged +
                        ", unpaged=" + unpaged +
                        '}';
            }

            public static class SortBean implements Serializable {
                /**
                 * sorted : true
                 * unsorted : false
                 */

                @SerializedName("sorted")
                private Boolean sorted;
                @SerializedName("unsorted")
                private Boolean unsorted;

                public Boolean getSorted() {
                    return sorted;
                }

                public Boolean getUnsorted() {
                    return unsorted;
                }

                @Override
                public String toString() {
                    return "SortBean{" +
                            "sorted=" + sorted +
                            ", unsorted=" + unsorted +
                            '}';
                }
            }
        }

        public static class SortBeanX implements Serializable {
            /**
             * sorted : true
             * unsorted : false
             */

            @SerializedName("sorted")
            private Boolean sorted;
            @SerializedName("unsorted")
            private Boolean unsorted;

            public Boolean getSorted() {
                return sorted;
            }

            public Boolean getUnsorted() {
                return unsorted;
            }

            @Override
            public String toString() {
                return "SortBeanX{" +
                        "sorted=" + sorted +
                        ", unsorted=" + unsorted +
                        '}';
            }
        }

        public static class ContentBean implements Serializable, Parcelable {
            /**
             * _id : 1019644039198146560
             * userId : 1499922423573647361
             * userName : null
             * avatar : null
             * cover : https://images.sunofbeaches.com/content/2022_09_14/1019643983925608448.png
             * name : api
             * permission : 1
             * description : test
             * createTime : 2022-09-14 16:21
             * followCount : 0
             * favoriteCount : 1
             */

            @SerializedName("_id")
            private String id;
            @SerializedName("userId")
            private String userId;
            @SerializedName("userName")
            private String userName;
            @SerializedName("avatar")
            private String avatar;
            @SerializedName("cover")
            private String cover;
            @SerializedName("name")
            private String name;
            @SerializedName("permission")
            private String permission;
            @SerializedName("description")
            private String description;
            @SerializedName("createTime")
            private String createTime;
            @SerializedName("followCount")
            private Integer followCount;
            @SerializedName("favoriteCount")
            private Integer favoriteCount;

            public String getId() {
                return id;
            }

            public String getUserId() {
                return userId;
            }

            public String  getUserName() {
                return userName;
            }

            public String getAvatar() {
                return avatar;
            }

            public String getCover() {
                return cover;
            }

            public String getName() {
                return name;
            }

            public String getPermission() {
                return permission;
            }

            public String getDescription() {
                return description;
            }

            public String getCreateTime() {
                return createTime;
            }

            public Integer getFollowCount() {
                return followCount;
            }

            public Integer getFavoriteCount() {
                return favoriteCount;
            }

            @Override
            public String toString() {
                return "ContentBean{" +
                        "id='" + id + '\'' +
                        ", userId='" + userId + '\'' +
                        ", userName=" + userName +
                        ", avatar=" + avatar +
                        ", cover='" + cover + '\'' +
                        ", name='" + name + '\'' +
                        ", permission='" + permission + '\'' +
                        ", description='" + description + '\'' +
                        ", createTime='" + createTime + '\'' +
                        ", followCount=" + followCount +
                        ", favoriteCount=" + favoriteCount +
                        '}';
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.id);
                dest.writeString(this.userId);
                dest.writeString(this.userName);
                dest.writeString(this.avatar);
                dest.writeString(this.cover);
                dest.writeString(this.name);
                dest.writeString(this.permission);
                dest.writeString(this.description);
                dest.writeString(this.createTime);
                dest.writeValue(this.followCount);
                dest.writeValue(this.favoriteCount);
            }

            public void readFromParcel(Parcel source) {
                this.id = source.readString();
                this.userId = source.readString();
                this.userName = source.readParcelable(Object.class.getClassLoader());
                this.avatar = source.readParcelable(Object.class.getClassLoader());
                this.cover = source.readString();
                this.name = source.readString();
                this.permission = source.readString();
                this.description = source.readString();
                this.createTime = source.readString();
                this.followCount = (Integer) source.readValue(Integer.class.getClassLoader());
                this.favoriteCount = (Integer) source.readValue(Integer.class.getClassLoader());
            }

            public ContentBean() {
            }

            protected ContentBean(Parcel in) {
                this.id = in.readString();
                this.userId = in.readString();
                this.userName = in.readParcelable(Object.class.getClassLoader());
                this.avatar = in.readParcelable(Object.class.getClassLoader());
                this.cover = in.readString();
                this.name = in.readString();
                this.permission = in.readString();
                this.description = in.readString();
                this.createTime = in.readString();
                this.followCount = (Integer) in.readValue(Integer.class.getClassLoader());
                this.favoriteCount = (Integer) in.readValue(Integer.class.getClassLoader());
            }

            public static final Creator<ContentBean> CREATOR = new Creator<ContentBean>() {
                @Override
                public ContentBean createFromParcel(Parcel source) {
                    return new ContentBean(source);
                }

                @Override
                public ContentBean[] newArray(int size) {
                    return new ContentBean[size];
                }
            };
        }
    }
}
