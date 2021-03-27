package com.hnimrod.chatview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class ChatViewAdapter extends ArrayAdapter<ChatEntity> {

    public static final int TYPE_RIGHT = ChatEntity.TYPE.RIGHT.ordinal();
    public static final int TYPE_LEFT = ChatEntity.TYPE.LEFT.ordinal();
    public static final int TYPE_HEADER = ChatEntity.TYPE.HEADER.ordinal();

    private static final CircleTransformation CIRCLE_TRANSFORMATION = new CircleTransformation();

    private Context mContext;
    private Drawable rightMessageDrawable;
    private Drawable leftMessageDrawable;
    private Integer rightMessageColor;
    private Integer leftMessageColor;

    public ChatViewAdapter(Context context) {
        super(context, 0);
        this.mContext = context;
    }

    public static class ViewHolderLeft {
        ImageView icon;
        TextView name;
        TextView message;
        TextView time;
        TextView readDone;

        public ViewHolderLeft(View view) {
            icon = (ImageView) view.findViewById(R.id.com_hnimrod_chatview_left_layout_icon);
            name = (TextView) view.findViewById(R.id.com_hnimrod_simple_chatview_left_layout_name);
            message = (TextView) view.findViewById(R.id.com_hnimrod_simple_chatview_left_layout_message);
            time = (TextView) view.findViewById(R.id.com_hnirmod_simple_chatview_left_layout_time);
            readDone = (TextView) view.findViewById(R.id.com_hnimrod_simple_chatview_left_layout_readdone);
        }
    }

    public static class ViewHolderRight {
        TextView message;
        TextView time;
        TextView readDone;
        ImageView icon;

        public ViewHolderRight(View view) {
            message = (TextView) view.findViewById(R.id.com_hnimrod_simple_chatview_right_layout_message);
            time = (TextView) view.findViewById(R.id.com_hnimrod_simple_chatview_right_layout_time);
            readDone = (TextView) view.findViewById(R.id.com_hnimrod_simple_chatview_right_layout_readdone);
            icon = (ImageView) view.findViewById(R.id.com_hnimrod_simple_chatview_right_layout_icon);
        }
    }

    public static class ViewHolderHeaderSeparator {
        TextView date;

        public ViewHolderHeaderSeparator(View view) {
            date = (TextView) view.findViewById(R.id.com_hnimrod_simple_chatview_header_layout_date);
        }
    }


    public String unixtimeToString(long unixtime) {
        return new SimpleDateFormat("HH:mm", Locale.JAPAN).format(new Date(unixtime * 1000));
    }


    private void setNameView(TextView view, @NonNull String str) {
        if (view == null || str == null) {
            return;
        }
        view.setText(str);
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        ChatEntity item = getItem(position);

        if (getItemViewType(position) == TYPE_RIGHT) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.lib_chatview_right_item_simple_layout, null);
                ViewHolderRight holder = new ViewHolderRight(convertView);
                convertView.setTag(holder);
            }
            ViewHolderRight holder = (ViewHolderRight) convertView.getTag();

            // utt
            setMessageView(holder.message, item.utt);

            // time
            setTimeView(holder.time, item.unixtime);

            // image
            setImageView(holder.icon, item.image);

            // readdone
            setReadDoneView(holder.readDone, item.readdone);

            // background
            setCustomDrawable(holder.message, rightMessageDrawable);

            // text color
            setCustomTextColor(holder.message, rightMessageColor);
        }
        else if (getItemViewType(position) == TYPE_LEFT) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.lib_chatview_left_item_simple_layout, null);
                ViewHolderLeft holder = new ViewHolderLeft(convertView);
                convertView.setTag(holder);
            }
            ViewHolderLeft holder = (ViewHolderLeft) convertView.getTag();

            // name
            setNameView(holder.name, TextUtils.isEmpty(item.name) ? "" : item.name);

            // utt
            setMessageView(holder.message, item.utt);

            // time
            setTimeView(holder.time, item.unixtime);

            // image
            setImageView(holder.icon, item.image);

            // background
            setCustomDrawable(holder.message, leftMessageDrawable);

            // text color
            setCustomTextColor(holder.message, leftMessageColor);
        }
        //
        // date separator view
        //
        else {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.lib_chatview_header_item, null);
                ViewHolderHeaderSeparator holder = new ViewHolderHeaderSeparator(convertView);
                convertView.setTag(holder);
            }
            ViewHolderHeaderSeparator holder = (ViewHolderHeaderSeparator) convertView.getTag();
            holder.date.setText(item.utt);
        }

        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        ChatEntity item = getItem(position);
        if (item.type == ChatEntity.TYPE.LEFT) {
            return TYPE_LEFT;
        } else if (item.type == ChatEntity.TYPE.RIGHT) {
            return TYPE_RIGHT;
        } else {
            return TYPE_HEADER;
        }
    }


    public void setRightMessageDrawable(@DrawableRes int drawable) {
        try {
            Drawable dw = getDrawableResource(drawable);
            if (dw != null) {
                rightMessageDrawable = dw;
            }
        } catch (Resources.NotFoundException e) {
            Log.d("ChatView", "runtime error occurs on setRightMessageDrawable: " + e.getMessage());
        }
    }

    public void setLeftMessageDrawable(@DrawableRes int drawable) {
        try {
            Drawable dw = getDrawableResource(drawable);
            if (dw != null) {
                leftMessageDrawable = dw;
            }
        } catch (Resources.NotFoundException e) {
            Log.d("ChatView", "runtime error occurs on setLeftMessageDrawable: " + e.getMessage());
        }
    }


    @SuppressWarnings("deprecation")
    private Drawable getDrawableResource(int id){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            return mContext.getDrawable(id);
        }
        else{
            return mContext.getResources().getDrawable(id);
        }
    }

    public void setRightMessageColor(@ColorInt Integer color) {
        rightMessageColor = color;
    }

    public void setLeftMessageColor(@ColorInt Integer color) {
        leftMessageColor = color;
    }



    private void setMessageView(TextView view, @NonNull String str) {
        if (view == null || str == null) {
            return;
        }
        view.setText(str);
    }

    private void setTimeView(TextView view, long unixtime) {
        if (view == null) {
            return;
        }

        String timeStr = unixtimeToString(unixtime);
        view.setText(timeStr);
        view.setVisibility(View.VISIBLE);
    }

    private void setReadDoneView(TextView view, boolean isReadDone) {
        view.setVisibility(isReadDone ? View.VISIBLE: View.GONE);
    }

    private void setImageView(ImageView view, String imageUrl) {
        view.setVisibility(TextUtils.isEmpty(imageUrl) ? View.GONE : View.VISIBLE);
        if (TextUtils.isEmpty(imageUrl)) {
            return;
        }
        Picasso.with(getContext()).load(imageUrl).transform(CIRCLE_TRANSFORMATION).into(view);
    }

    private void setImageView(ImageView view, @DrawableRes int rid) {
        view.setImageResource(rid);
    }

    private void setCustomDrawable(View view, Drawable drawable) {
        if (drawable != null) {
            view.setBackground(drawable);
        }
    }

    private void setCustomTextColor(TextView view, Integer color) {
        if (color != null) {
            view.setTextColor(color);
        }
    }


}