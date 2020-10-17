package com.ds.assignment.ui.main.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ds.assignment.R;
import com.ds.assignment.data.model.MessageModel;
import com.ds.assignment.databinding.AgentMessageLayoutBinding;
import com.ds.assignment.databinding.UserMessageLayoutBinding;

import java.util.List;

public class IndividualMessagesAdapter extends RecyclerView.Adapter {

    private static final int VIEW_TYPE_AGENT_MESSAGE = 1;
    private static final int VIEW_TYPE_USER_MESSAGE = 2;

    public void setMessageList(List<MessageModel> messageList) {
        this.messageList = messageList;
    }

    private List<MessageModel> messageList;
    private Context mContext;

    public IndividualMessagesAdapter(Context context, List<MessageModel> list) {
        mContext = context;
        messageList = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        if (viewType == VIEW_TYPE_AGENT_MESSAGE) {
            AgentMessageLayoutBinding binding =  DataBindingUtil.inflate(layoutInflater, R.layout.agent_message_layout, parent, false);
            return new AgentMessageHolder(binding);
        } else if (viewType == VIEW_TYPE_USER_MESSAGE) {
            UserMessageLayoutBinding binding =  DataBindingUtil.inflate(layoutInflater, R.layout.user_message_layout, parent, false);
            return new UserMessageHolder(binding);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MessageModel message = messageList.get(position);

        switch (holder.getItemViewType()) {
            case VIEW_TYPE_AGENT_MESSAGE:
                ((AgentMessageHolder) holder).bind(message);
                break;
            case VIEW_TYPE_USER_MESSAGE:
                ((UserMessageHolder) holder).bind(message);
        }
    }

    @Override
    public int getItemCount() {
        if (messageList == null)
            return 0;
        return messageList.size();
    }

    @Override
    public int getItemViewType(int position) {
//        return super.getItemViewType(position);

        MessageModel message = messageList.get(position);

        if (TextUtils.isEmpty(message.getAgentId())) {
            //User Messages
            return VIEW_TYPE_USER_MESSAGE;
        } else {
            //Agent Messages
            return VIEW_TYPE_AGENT_MESSAGE;
        }
    }

    public class AgentMessageHolder extends RecyclerView.ViewHolder {

        private AgentMessageLayoutBinding binding;

        public AgentMessageHolder(@NonNull AgentMessageLayoutBinding layoutMessageItemBinding) {
            super(layoutMessageItemBinding.getRoot());
            binding = layoutMessageItemBinding;
        }

        public void bind(MessageModel messageModel) {
            binding.tvAgentMessage.setText(messageModel.getBody());
            binding.tvAgentId.setText("Agent Id: " + messageModel.getAgentId());
        }
    }

    public class UserMessageHolder extends RecyclerView.ViewHolder {

        private UserMessageLayoutBinding binding;

        public UserMessageHolder(@NonNull UserMessageLayoutBinding layoutMessageItemBinding) {
            super(layoutMessageItemBinding.getRoot());
            binding = layoutMessageItemBinding;
        }

        public void bind(MessageModel messageModel) {
            binding.tvUserMessage.setText(messageModel.getBody());
            binding.tvUserId.setText("User Id: " + messageModel.getUserId());
        }
    }
}
