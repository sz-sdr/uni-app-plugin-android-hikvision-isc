package com.uniapp.plugin.hikvisionisc;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.afollestad.materialdialogs.MaterialDialog;
import com.uniapp.library.baselib.ui.tree.TreeNode;
import com.uniapp.library.baselib.ui.tree.TreeNodeRecyclerAdapter;
import com.uniapp.plugin.hikvisionisc.entity.Cameras;
import com.uniapp.plugin.hikvisionisc.entity.RegionsAndCameras;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static android.os.Environment.DIRECTORY_MOVIES;
import static android.os.Environment.DIRECTORY_PICTURES;

/**
 * Created by HyFun on 2019/10/21.
 * Email: 775183940@qq.com
 * Description:
 */
class Util {

    public static class RxUtils {

        /**
         * 统一线程处理
         *
         * @param <T> 指定的泛型类型
         * @return ObservableTransformer
         */
        public final static <T> ObservableTransformer<T, T> io_main() {
            return new ObservableTransformer<T, T>() {
                @Override
                public ObservableSource<T> apply(Observable<T> observable) {
                    return observable.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread());
                }
            };
        }


        /**
         * 得到 Observable
         *
         * @param <T> 指定的泛型类型
         * @return Observable
         */
        public static <T> Observable<T> createData(final T t) {
            return Observable.create(new ObservableOnSubscribe<T>() {
                @Override
                public void subscribe(ObservableEmitter<T> emitter) throws Exception {
                    try {
                        emitter.onNext(t);
                        emitter.onComplete();
                    } catch (Exception e) {
                        emitter.onError(e);
                    }
                }
            });
        }

        /**
         * 验证base data
         *
         * @param <T>
         * @return
         */
        public static <T> ObservableTransformer<Entity.BaseData<T>, T> baseData() {
            return new ObservableTransformer<Entity.BaseData<T>, T>() {
                @Override
                public ObservableSource<T> apply(Observable<Entity.BaseData<T>> upstream) {
                    return upstream.flatMap(new Function<Entity.BaseData<T>, ObservableSource<T>>() {
                        @Override
                        public ObservableSource<T> apply(Entity.BaseData<T> baseData) throws Exception {
                            if (baseData.getCode() == Entity.BaseData.SUCCESS) {
                                return RxUtils.createData(baseData.getData());
                            } else {
                                String error = baseData.getMsg();
                                error = TextUtils.isEmpty(error) ? "请求错误" : error;
                                return Observable.error(new Exception("错误：" + baseData.getCode() + "," + error));
                            }
                        }
                    });
                }
            };
        }


        /**
         * 转换成HIKBasedata的转换
         *
         * @param <T>
         */
//        @Deprecated
//        public static class RxTransformer<T> implements ObservableTransformer<ResponseBody, Entity.HIKBaseData<T>> {
//
//            @Override
//            public ObservableSource<Entity.HIKBaseData<T>> apply(Observable<ResponseBody> upstream) {
//                return upstream.flatMap(new Function<ResponseBody, ObservableSource<Entity.HIKBaseData<T>>>() {
//                    @Override
//                    public ObservableSource<Entity.HIKBaseData<T>> apply(ResponseBody responseBody) throws Exception {
//                        String json = responseBody.string();
//                        if (TextUtils.isEmpty(json)) {
//                            return Observable.error(new Exception("获取数据异常"));
//                        } else {
//                            Entity.HIKBaseData<T> t = HttpClient.gson.fromJson(json, new TypeToken<Entity.HIKBaseData<T>>() {
//                            }.getType());
////                            T t = JSON.parseObject(json, new TypeReference<T>() {
////                            });
//                            return RxUtils.createData(t);
//                        }
//                    }
//                });
//            }
//        }


        /**
         * 验证base data
         *
         * @param <T>
         * @return
         */
        public static <T> ObservableTransformer<Entity.HIKBaseData<T>, T> baseHKData() {
            return new ObservableTransformer<Entity.HIKBaseData<T>, T>() {
                @Override
                public ObservableSource<T> apply(Observable<Entity.HIKBaseData<T>> upstream) {
                    return upstream.flatMap(new Function<Entity.HIKBaseData<T>, ObservableSource<T>>() {
                        @Override
                        public ObservableSource<T> apply(Entity.HIKBaseData<T> baseData) throws Exception {
                            if (baseData.getCode().equals(Entity.HIKBaseData.SUCCESS)) {
                                return RxUtils.createData(baseData.getData());
                            } else {
                                String error = baseData.getMsg();
                                error = TextUtils.isEmpty(error) ? "请求错误" : error;
                                return Observable.error(new Exception("错误：" + baseData.getCode() + "," + error));
                            }
                        }
                    });
                }
            };
        }


        /**
         * 验证base data
         *
         * @param <T>
         * @return
         */
        public static <T> ObservableTransformer<Entity.HIKBaseData<T>, Boolean> baseHKBoolean() {
            return new ObservableTransformer<Entity.HIKBaseData<T>, Boolean>() {
                @Override
                public ObservableSource<Boolean> apply(Observable<Entity.HIKBaseData<T>> upstream) {
                    return upstream.flatMap(new Function<Entity.HIKBaseData<T>, ObservableSource<Boolean>>() {
                        @Override
                        public ObservableSource<Boolean> apply(Entity.HIKBaseData<T> baseData) throws Exception {
                            if (baseData.getCode().equals(Entity.HIKBaseData.SUCCESS)) {
                                return RxUtils.createData(true);
                            } else {
                                String error = baseData.getMsg();
                                error = TextUtils.isEmpty(error) ? "请求错误" : error;
                                return Observable.error(new Exception("错误：" + baseData.getCode() + "," + error));
                            }
                        }
                    });
                }
            };
        }


    }

    /**
     * 解析后台返回的摄像头列表，递归生成treeNodeList
     */
    public static class ResourceUtil {
        private List<TreeNode> treeNodeList = new ArrayList<>();

        public ResourceUtil(RegionsAndCameras regionsAndCameras) {
            getRegionList(regionsAndCameras);
        }

        /**
         * 获取列表
         *
         * @return
         */
        public List<TreeNode> getList() {
            return treeNodeList;
        }

        /**
         * 递归调用
         *
         * @param regionsAndCameras
         */
        private void getRegionList(RegionsAndCameras regionsAndCameras) {
            treeNodeList.add(new TreeNode(regionsAndCameras.getIndexCode(), regionsAndCameras.getParentIndexCode(), regionsAndCameras.getName(), false, false, regionsAndCameras));
            List<Cameras> cameras = regionsAndCameras.getCameras();
            // 摄像头
            if (cameras == null) {
                cameras = new ArrayList<>();
            }
            for (int i = 0; i < cameras.size(); i++) {
                Cameras came = cameras.get(i);
                treeNodeList.add(new TreeNode(came.getCameraIndexCode(), came.getRegionIndexCode(), came.getCameraName(), false, true, came));
            }
            // 区域
            List<RegionsAndCameras> child = regionsAndCameras.getChild();
            if (child == null) {
                child = new ArrayList<>();
            }
            for (int i = 0; i < child.size(); i++) {
                RegionsAndCameras region = child.get(i);
                getRegionList(region);
            }
        }
    }

    /**
     * 显示设备列表的dialog
     */
    public static class HKVideoPlayListDialog {
        private Context mContext;
        private List<TreeNode> mTreeNodeList;
        private TreeNodeRecyclerAdapter.OnTreeNodeSigleClickListener mOnTreeNodeSigleClickListener;

        public HKVideoPlayListDialog(Context context, List<TreeNode> treeNodeList, TreeNodeRecyclerAdapter.OnTreeNodeSigleClickListener onTreeNodeSigleClickListener) {
            mContext = context;
            mTreeNodeList = treeNodeList;
            mOnTreeNodeSigleClickListener = onTreeNodeSigleClickListener;
        }

        private MaterialDialog dialog;

        public void show() {
            try {
                RecyclerView recyclerView = new RecyclerView(mContext);
                TreeNodeRecyclerAdapter adapter = new TreeNodeRecyclerAdapter(mContext, mTreeNodeList, new TreeNodeRecyclerAdapter.OnTreeNodeSigleClickListener() {
                    @Override
                    public void onSigleClick(TreeNode treeNode, int visablePositon, int realDatasPositon, boolean isLeaf) {
                        if (isLeaf && dialog != null) {
                            dialog.dismiss();
                            if (mOnTreeNodeSigleClickListener != null)
                                mOnTreeNodeSigleClickListener.onSigleClick(treeNode, visablePositon, realDatasPositon, isLeaf);
                        }
                    }
                }, 2);
                recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                recyclerView.setAdapter(adapter);
                dialog = new MaterialDialog.Builder(mContext)
                        .title("请选择监控点")
                        .customView(recyclerView, false)
                        .show();
            } catch (Exception e) {
            }
        }
    }


    /**
     * 抓图路径格式：/storage/emulated/0/Android/data/com.hikvision.open.app/files/Pictures/_20180917151634445.jpg
     */
    public static String getCaptureImagePath(Context context) {
        File file = context.getExternalFilesDir(DIRECTORY_PICTURES);
        String path = file.getAbsolutePath() + File.separator + Util.getFileName() + ".jpg";
        return path;
    }


    /**
     * 录像路径格式：/storage/emulated/0/Android/data/com.hikvision.open.app/files/Movies/_20180917151636872.mp4
     */
    public static String getLocalRecordPath(Context context) {
        File file = context.getExternalFilesDir(DIRECTORY_MOVIES);
        String path = file.getAbsolutePath() + File.separator + Util.getFileName() + ".mp4";
        return path;
    }

    /**
     * 根据时间生成文件名称
     *
     * @return
     */
    public static String getFileName() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return dateFormat.format(new Date());
    }


    /**
     * 通知相册更新了
     */
    public static void notifyPhotoChanged(Context context, String filePath) {
        Uri uri = Uri.fromFile(new File(filePath));
        // 通知图库更新
        Intent scannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
        context.sendBroadcast(scannerIntent);
    }

}
