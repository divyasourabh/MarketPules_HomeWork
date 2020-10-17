package com.ds.assignment.ui.main.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ds.assignment.R;
import com.ds.assignment.data.model.MessageModel;
import com.ds.assignment.databinding.LayoutMessageListItemBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AllMessageListAdapter extends RecyclerView.Adapter<AllMessageListAdapter.ListViewHolder> {

    private Context mContext;
    private ThreadClickListener threadClickListener;

    public void setList(List<MessageModel> list) {
        this.list = list;
    }

    public List<MessageModel> list;

    public AllMessageListAdapter(Context context, List<MessageModel> consolidateList){
        this.list = consolidateList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        LayoutMessageListItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.layout_message_list_item, parent, false);
        return new ListViewHolder(binding,threadClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        if (list == null)
            return 0;
        return list.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {

        private LayoutMessageListItemBinding binding;
        private ThreadClickListener threadClickListener;

        public ListViewHolder(@NonNull LayoutMessageListItemBinding viewDataBinding, ThreadClickListener threadClickListener) {
            super(viewDataBinding.getRoot());
            binding = viewDataBinding;
            this.threadClickListener = threadClickListener;

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    threadClickListener.onThreadClick(list.get(getLayoutPosition()).getThreadId());
                }
            });
        }

        public void bind(MessageModel messageModel) {
            binding.tvThreadId.setText("Thread-Id: " + messageModel.getThreadId());

            binding.tvMessageBody.setText(messageModel.getBody());
            if (!TextUtils.isEmpty(messageModel.getUserId())) {
                binding.tvUserAgentId.setText("User Id: " + messageModel.getUserId());
            } else if (!TextUtils.isEmpty(messageModel.getAgentId())) {
                binding.tvUserAgentId.setText("Agent Id: " + messageModel.getAgentId());
            }

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);

            Date date1 = null;
            try {
                date1 = format.parse(messageModel.getTimestamp());
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (date1 != null) {
                binding.tvTimestamp.setText(date1.toString());
                binding.tvTimestamp.setVisibility(View.VISIBLE);
            } else {
                binding.tvTimestamp.setVisibility(View.GONE);
            }
        }
    }

    public void setThreadClickListener(ThreadClickListener threadClickListener) {
        this.threadClickListener = threadClickListener;
    }
}
