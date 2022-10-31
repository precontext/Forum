package com.program.module_ucenter.model;

import com.program.module_ucenter.model.domain.AchievementBean;
import com.program.moudle_base.model.AddOrUnFollowBean;
import com.program.module_ucenter.model.domain.ArticleBean;
import com.program.module_ucenter.model.domain.AvaTarBean;
import com.program.moudle_base.model.FollowBean;
import com.program.module_ucenter.model.domain.LoginoutBean;
import com.program.module_ucenter.model.domain.MoyuBean;
import com.program.module_ucenter.model.domain.MsgAtBean;
import com.program.module_ucenter.model.domain.MsgSystemBean;
import com.program.module_ucenter.model.domain.ReadAllBean;
import com.program.module_ucenter.model.domain.ShareBean;
import com.program.module_ucenter.model.domain.UnreadMsgBean;
import com.program.module_ucenter.model.domain.UserInfoBean;
import com.program.module_ucenter.model.domain.UserMessageBean;
import com.program.module_ucenter.model.domain.UserWendaBean;
import com.program.moudle_base.model.MoyuRequestBean;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UcenterApi {

    @GET("/ct/moyu/{moyuId}")
    Observable<MoyuRequestBean> getMoyuDetail(@Path("moyuId")String id, @Header("sob_token")String token);

    @GET("/uc/user/avatar/{phoneNum}")
    Observable<AvaTarBean> getAvatar(@Path("phoneNum") String phoneNum);

    @GET("/uc/user-info/{userId}")
    Observable<UserMessageBean> getUserMessage(@Path("userId")String id);

    /**
     * 获取用户成就，阅读量，点赞量之类
     * @param id    id
     * @return      bean
     */
    @GET("/ast/achievement/{userId}")
    Observable<AchievementBean> getUserAchievementBean(@Path("userId") String id);

    @GET("/uc/user/logout")
    Observable<LoginoutBean> loginout(@Header("sob_token")String token);

    @GET("/ct/msg/count")
    Observable<UnreadMsgBean> getUnreadBean(@Header("sob_token")String token);

    @GET("/ct/msg/read")
    Observable<ReadAllBean> readAllMsg(@Header("sob_token")String token);

    @GET("/ct/ucenter/message/system/{page}")
    Observable<MsgSystemBean> getSystemMsg(@Path("page")int page,@Header("sob_token")String token);

    @GET("/ct/ucenter/message/at/{page}")
    Observable<MsgAtBean> getMsgAtList(@Path("page")int page,@Header("sob_token")String token);

    /**
     *  得到摸鱼列表
     * @param id    目标id
     * @param page  页码
     * @param token 自己的token       没有这个就只允许访问一页摸鱼
     * @return      return
     */
    @GET("/ct/moyu/list/user/{userId}/{page}")
    Observable<MoyuBean> getMoyuList(@Path("userId") String id, @Path("page") int page,@Header("sob_token")String token);

    /**
     * 获取用户信息
     * @param userId    id
     * @return          UserInfoBean
     */
    @GET("/uc/user-info/{userId}")
    Observable<UserInfoBean> getUserInfo(@Path("userId")String userId);

    /**
     * 查看受否关注
     * @param userId    对象id
     * @param token     自己token
     * @return          bean
     */
    @GET("/uc/fans/state/{userId}")
    Observable<FollowBean> getFollowState(@Path("userId")String userId,@Header("sob_token")String token);

    /**
     * 添加关注
     * @param userId    用户id
     * @param token     自己token
     * @return
     */
    @POST("/uc/fans/{userId}")
    Observable<AddOrUnFollowBean> addFollow(@Path("userId")String userId, @Header("sob_token")String token);

    @DELETE("/uc/fans/{userId}")
    Observable<AddOrUnFollowBean> unFollow(@Path("userId")String userId, @Header("sob_token")String token);

    /**
     * 获取用户文章列表
     * @param userId    用户id
     * @param page      页码
     * @return      文章列表
     */
    @GET("/ct/article/list/{userId}/{page}")
    Observable<ArticleBean> getArticle(@Path("userId")String userId,@Path("page")int page, @Header("sob_token")String token);

    /**
     * 获取用户分享
     * @param userId    用户id
     * @param page      页码
     * @return      分享列表
     */
    @GET("/ct/share/list/{userId}/{page}")
    Observable<ShareBean> getShare(@Path("userId")String userId, @Path("page")int page, @Header("sob_token")String token);

    /**
     * 获取用户问答列表
     * @param userId    用户id
     * @param page      页码
     * @return           问答列表
     */
    @GET("/ct/wenda/comment/list/user/{userId}/{page}")
    Observable<UserWendaBean> getWenda(@Path("userId")String userId, @Path("page")int page, @Header("sob_token")String token);
}
