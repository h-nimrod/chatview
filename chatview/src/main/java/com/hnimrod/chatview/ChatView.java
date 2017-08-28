package com.hnimrod.chatview;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class ChatView extends FrameLayout {

    private final static String TAG = "ChatView";
    private final int ERROR_ADD = -1;

    private Context mContext;
    private boolean mShowReverseOrder = false;
    private List<ChatEntity> mItemList;
    private ChatViewAdapter mListAdapter;

    View mRootView;
    ListView mListView;
    LinearLayout mEditTextArea;
    EditText mMessageEditText;
    Button mSubmitButton;

    /**
     * コンストラクタ
     *
     * @param context Application context
     * @param attrs   Attributes
     */
    public ChatView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.mContext = context;

        mRootView = LayoutInflater.from(context).inflate(R.layout.lib_chatview_base_layout, this, true);

        if (isInEditMode()) {
            return;
        }

        mItemList = new ArrayList<>();
        bindViews(mRootView);
        setupListView();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        mItemList = null;
        mListAdapter = null;
        mContext = null;
        super.onDetachedFromWindow();
    }

    /**
     * 表示順を設定
     * @param reverse true: 新しいアイテムが上, false: 新しいアイテムが下
     */
    public void setShowReverseOrder(boolean reverse) {
        mShowReverseOrder = reverse;

        update();

        if (mShowReverseOrder) {
            mListView.setTranscriptMode(ListView.TRANSCRIPT_MODE_DISABLED);
            mListView.setSelectionAfterHeaderView();
        } else {
            mListView.setTranscriptMode(ListView.TRANSCRIPT_MODE_NORMAL);
            if (mItemList.size() > 0) {
                mListView.setSelection(mItemList.size() - 1);
            }
        }
    }

    /**
     * 右(自分)のメッセージ領域の背景画像を設定
     * @param drawable
     */
    public void setRightBackgroundDrawable(@DrawableRes int drawable) {
        if (mListAdapter != null) {
            mListAdapter.setRightMessageDrawable(drawable);
        }
    }

    /**
     * 左(相手)のメッセージ領域の背景画像を設定
     * @param drawable
     */
    public void setLeftBackgroundDrawable(@DrawableRes int drawable) {
        if (mListAdapter != null) {
            mListAdapter.setLeftMessageDrawable(drawable);
        }
    }

    /**
     * 右(自分)のメッセージの文字色を設定
     * @param color
     */
    public void setRightMessageColor(@ColorInt Integer color) {
        if (mListAdapter != null) {
            mListAdapter.setRightMessageColor(color);
        }
    }

    /**
     * 左(相手)のメッセージの文字色を設定
     * @param color
     */
    public void setLeftMessageColor(@ColorInt Integer color) {
        if (mListAdapter != null) {
            mListAdapter.setLeftMessageColor(color);
        }
    }

    /**
     * 右(自分)のメッセージを追加
     *
     * @return 追加したListの位置
     * @param message メッセージ
     */
    public int addRightMessage(String message) {
        return addRightMessage(message, null);
    }

    /**
     * 右(自分)のメッセージを追加
     *
     * @return 追加したListの位置
     * @param message メッセージ
     * @param imageUrl 表示する画像のURL
     */
    public int addRightMessage(String message, String imageUrl) {
        ChatEntity item = createItem(message, imageUrl);
        item.setRight();
        return add(item);
    }

    /**
     * 左(相手)のメッセージを追加
     *
     * @return 追加したListの位置
     * @param message メッセージ
     */
    public int addLeftMessage(String message) {
        return addLeftMessage(message);
    }

    /**
     * 左(相手)のメッセージを追加
     *
     * @return 追加したListの位置
     * @param message メッセージ
     * @param imageUrl 表示する画像のURL
     */
    public int addLeftMessage(String message, @NonNull String imageUrl) {
        ChatEntity item = createItem(message, imageUrl);
        item.setLeft();
        return add(item);
    }

    /**
     * アイテム追加
     *
     * @return 追加したListの位置
     * @param item 追加するアイテム
     */
    public int add(ChatEntity item) {
        boolean success = mItemList.add(item);
        update();

        return success ? mItemList.size() - 1 : ERROR_ADD;
    }

    /**
     * 場所指定でのアイテム追加
     *
     *
     * @param idx インデックス
     * @param item
     */
    public int add(int idx, ChatEntity item) {
        mItemList.add(idx, item);
        update();

        return idx;
    }

    /**
     * アイテム追加
     *
     * @param collection
     */
    public void addAll(Collection<? extends ChatEntity> collection) {
        mItemList.addAll(collection);
        update();
    }

    /**
     * アイテム追加
     * @param items
     */
    public void addAll(ChatEntity... items) {
        mItemList.addAll(Arrays.asList(items));
        update();
    }

    /**
     * View に追加されているアイテムの取得
     *
     * @param idx リスト位置
     * @return
     */
    public ChatEntity getItem(int idx) {
        if (mItemList == null || idx < 0 || idx >= mItemList.size()) {
            return null;
        }
        return mItemList.get(idx);
    }

    /**
     * View に追加されているアイテムの取得
     * @return
     */
    public List<ChatEntity> getAll() {
        return mItemList;
    }

    /**
     * メッセージ表示を削除
     */
    public void clear() {
        mItemList.clear();
        update();
    }


    // private

    private void update() {
        mListAdapter.clear();
        if (mShowReverseOrder) {
            List<ChatEntity> reverse = new ArrayList<>(mItemList);
            Collections.reverse(reverse);
            mListAdapter.addAll(reverse);
        } else {
            mListAdapter.addAll(mItemList);
        }

        mListAdapter.notifyDataSetChanged();
    }

    private long getCurrentUnixtime() {
        return System.currentTimeMillis() / 1000L;
    }

    private ChatEntity createItem(String message, String imageUrl) {
        ChatEntity.Builder builder = new ChatEntity.Builder()
                .setMessage(message);
        if (imageUrl != null) {
            builder.setImage(imageUrl);
        }
        return builder.create();
    }

    private void bindViews(View view) {
        mListView = (ListView) view.findViewById(com.hnimrod.chatview.R.id.custom_list2);
        mEditTextArea = (LinearLayout) view.findViewById(com.hnimrod.chatview.R.id.custom_edit_text_area2);
        mMessageEditText = (EditText) view.findViewById(com.hnimrod.chatview.R.id.custom_edit_text2);
        mSubmitButton = (Button) view.findViewById(com.hnimrod.chatview.R.id.custom_submit_button2);
    }

    private void setupListView() {
        mListAdapter = new ChatViewAdapter(getContext());
        mListView.setAdapter(mListAdapter);

        if (!mShowReverseOrder) {
            mListView.setTranscriptMode(ListView.TRANSCRIPT_MODE_NORMAL);
        }

        mListView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    hideKeyboard(getContext(), getRootView());
                    getRootView().requestFocus();
                }
                return false;
            }
        });
    }

    private void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
