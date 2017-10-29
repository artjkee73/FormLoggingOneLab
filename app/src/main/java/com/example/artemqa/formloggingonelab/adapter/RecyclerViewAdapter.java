package com.example.artemqa.formloggingonelab.adapter;

import android.support.v4.widget.CompoundButtonCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.artemqa.formloggingonelab.R;
import com.example.artemqa.formloggingonelab.model.User;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by artemqa on 29.10.2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.CustomViewHolder> {
    RealmResults<User> itemsList;

    public RecyclerViewAdapter(RealmResults<User> listUsers) {
        itemsList = listUsers;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_recyclerview, parent, false);
        CustomViewHolder cvh = new CustomViewHolder(v);
        return cvh;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        holder.tvUserName.setText(itemsList.get(position).getLogin());
        holder.cbBlocked.setChecked(itemsList.get(position).isBlocked());
        holder.cbLimitation.setChecked(itemsList.get(position).isPasLimitation());
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }


    public static class CustomViewHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener {
        private final static String LOG ="MyLog";
        private TextView tvUserName;
        private CheckBox cbBlocked, cbLimitation;
        Realm realm;

        private CustomViewHolder(View itemView) {
            super(itemView);
            tvUserName = itemView.findViewById(R.id.tv_username_item_recycler_view);
            cbBlocked = itemView.findViewById(R.id.cb_blocked_item_recycler_view);
            cbBlocked.setOnCheckedChangeListener(this);
            cbLimitation = itemView.findViewById(R.id.cb_limitation_item_recycler_view);
            cbLimitation.setOnCheckedChangeListener(this);
            realm = Realm.getDefaultInstance();

        }

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            switch (compoundButton.getId()) {
                case R.id.cb_blocked_item_recycler_view:
                    setUserCB(b, R.id.cb_blocked_item_recycler_view);
                    break;
                case R.id.cb_limitation_item_recycler_view:
                    setUserCB(b, R.id.cb_limitation_item_recycler_view);
                    break;
            }

        }

        private void setUserCB(final boolean b, final int compoundId) {

            final User user = realm.where(User.class)
                    .equalTo("mLogin", tvUserName.getText().toString())
                    .findFirst();

            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {

                    if ( compoundId == R.id.cb_blocked_item_recycler_view)
                        user.setBlocked(b);
                    else
                        user.setPasLimitation(b);

                    realm.copyToRealmOrUpdate(user);
                }
            });
        }

    }
}
