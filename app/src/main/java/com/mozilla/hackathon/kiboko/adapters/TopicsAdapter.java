package com.mozilla.hackathon.kiboko.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.mozilla.hackathon.kiboko.Analytics;
import com.mozilla.hackathon.kiboko.R;
import com.mozilla.hackathon.kiboko.activities.FindIconsActivity;
import com.mozilla.hackathon.kiboko.activities.TutorialSlideActivity;
import com.mozilla.hackathon.kiboko.models.Topic;
import com.mozilla.hackathon.kiboko.settings.SettingsUtils;

import org.sufficientlysecure.htmltextview.EmojiUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brian Mwadime on 01/06/2016.
 */
public class TopicsAdapter  extends BaseAdapter implements Filterable {
    List<Topic> topics;
    private Context context;
    private Filter topicFilter;
    private List<Topic> origTopicList;

    public TopicsAdapter(Context ctx, List<Topic> topics) {
        this.topics = topics;
        this.context = ctx;
        this.origTopicList = topics;
    }
    @Override
    public int getCount() {
        return topics.size();
    }

    @Override
    public Topic getItem(int position) {
        return topics.get(position);
    }

    @Override
    public long getItemId(int position) {
        return topics.get(position).hashCode();
    }

    /* *********************************
	 * We use the holder pattern
	 * It makes the view faster and avoid finding the component
	 * **********************************/

    private static class Holder
    {
        TextView tv;
        ImageView img;
    }

    public void resetData() {
        topics = origTopicList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View viewItem = convertView;

        Holder holder = new Holder();

        // First let's verify the convertView is not null
        if (convertView == null) {
            // This a new view we inflate the new layout
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewItem = inflater.inflate(R.layout.dashboard_grid_item, null);
            // Now we can fill the layout with the right values
            TextView nameView = (TextView) viewItem.findViewById(R.id.dashboard_text);
            ImageView imageView = (ImageView) viewItem.findViewById(R.id.dashboard_icon);


            holder.tv = nameView;
            holder.img = imageView;

            viewItem.setTag(holder);
        }
        else
            holder = (Holder) viewItem.getTag();

        final Topic topic = topics.get(position);
        holder.tv.setText(topic.getName());
        
        if(SettingsUtils.isFunModeEnabled(context)){
            holder.tv.setText(EmojiUtils.parse(topic.getName()));
        }else{
            holder.tv.setText(topic.getName());
        }
        holder.img.setImageResource(topic.getImage());

        viewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Analytics.add("TopicsAdapter::Clicked", topic.getTag());
                if(topic.getTag().equals("icons")){
                    Intent topicIntent = new Intent(context, FindIconsActivity.class);
//                    topicIntent.putExtra("topic", topic.getName());
                    context.startActivity(topicIntent);
                }else{
                    Intent topicIntent = new Intent(context, TutorialSlideActivity.class);
                    topicIntent.putExtra("title", topic.getName());
                    topicIntent.putExtra("topic", topic.getTag());
                    context.startActivity(topicIntent);
                }

            }
        });


        return viewItem;
    }

    /*
	 * We create our filter
	 */

    @Override
    public Filter getFilter() {
        if (topicFilter == null)
            topicFilter = new TopicsFilter();

        return topicFilter;
    }



    private class TopicsFilter extends Filter {



        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            // We implement here the filter logic
            if (constraint == null || constraint.length() == 0) {
                // No filter implemented we return all the list
                results.values = origTopicList;
                results.count = origTopicList.size();
            }
            else {
                // We perform filtering operation
                List<Topic> nTopicList = new ArrayList<Topic>();

                for (Topic topic : topics) {
                    if (topic.getName().toUpperCase().startsWith(constraint.toString().toUpperCase()))
                        nTopicList.add(topic);
                }

                results.values = nTopicList;
                results.count = nTopicList.size();

            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {

            // Now we have to inform the adapter about the new list filtered
            if (results.count == 0)
                notifyDataSetInvalidated();
            else {
                topics = (List<Topic>) results.values;
                notifyDataSetChanged();
            }

        }

    }

}