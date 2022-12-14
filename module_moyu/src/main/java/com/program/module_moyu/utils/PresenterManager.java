package com.program.module_moyu.utils;

import com.program.module_moyu.presenter.IFishPoneSelectionActivityPresenter;
import com.program.module_moyu.presenter.IMoyuCommentDetailActivityPresenter;
import com.program.module_moyu.presenter.IMoyuDetailPresenter;
import com.program.module_moyu.presenter.IMoyuListFragmentPresenter;
import com.program.module_moyu.presenter.IMoyuMainFragmentPresenter;
import com.program.module_moyu.presenter.IPutFishActivityPresenter;
import com.program.module_moyu.presenter.Impl.FishPoneSelectionActivityPresenterImpl;
import com.program.module_moyu.presenter.Impl.MoyuCommentDetailActivityPresenterImpl;
import com.program.module_moyu.presenter.Impl.MoyuDetailPresenterImpl;
import com.program.module_moyu.presenter.Impl.MoyuListPresenterImpl;
import com.program.module_moyu.presenter.Impl.MoyuMainFragmentPresenterImpl;
import com.program.module_moyu.presenter.Impl.PutFishActivityPresenterImpl;

public class PresenterManager {
    private static final PresenterManager ourInstance = new PresenterManager();
    private final IMoyuDetailPresenter mMoyuDetailPresenter;
    private final IMoyuMainFragmentPresenter mMoyuMainFragmentPresenter;
    private final IMoyuListFragmentPresenter mMoyuListPresenter;
    private final IMoyuCommentDetailActivityPresenter mMoyuCommentDetailActivityPresenter;
    private final IFishPoneSelectionActivityPresenter mFishPoneSelectionActivityPresenter;
    private final IPutFishActivityPresenter mPutFishActivityPresenter;

    public static PresenterManager getInstance(){
        return ourInstance;
    }

    public IMoyuDetailPresenter getMoyuDetailPresenter() {
        return mMoyuDetailPresenter;
    }

    public IMoyuMainFragmentPresenter getMoyuMainFragmentPresenter() {
        return mMoyuMainFragmentPresenter;
    }

    public IMoyuListFragmentPresenter getMoyuListPresenter() {
        return mMoyuListPresenter;
    }

    public IMoyuCommentDetailActivityPresenter getMoyuCommentDetailActivityPresenter() {
        return mMoyuCommentDetailActivityPresenter;
    }

    public IFishPoneSelectionActivityPresenter getFishPoneSelectionActivityPresenter() {
        return mFishPoneSelectionActivityPresenter;
    }

    public IPutFishActivityPresenter getPutFishActivityPresenter() {
        return mPutFishActivityPresenter;
    }

    private PresenterManager(){
        mMoyuDetailPresenter = new MoyuDetailPresenterImpl();
        mMoyuMainFragmentPresenter = new MoyuMainFragmentPresenterImpl();
        mMoyuListPresenter = new MoyuListPresenterImpl();
        mMoyuCommentDetailActivityPresenter = new MoyuCommentDetailActivityPresenterImpl();
        mFishPoneSelectionActivityPresenter = new FishPoneSelectionActivityPresenterImpl();
        mPutFishActivityPresenter = new PutFishActivityPresenterImpl();
    }
}
