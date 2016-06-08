package com.mozilla.hackathon.kiboko.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.mozilla.hackathon.kiboko.R;
import com.mozilla.hackathon.kiboko.models.TopicItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brian Mwadime on 01/06/2016.
 */
public class TopicListAdapter extends BaseAdapter implements Filterable {
    List<TopicItem> topics;
    private Context context;
    private Filter planetFilter;
    private List<TopicItem> origTopicList;

    public TopicListAdapter(Context ctx, List<TopicItem> topics) {
        this.topics = topics;
        this.context = ctx;
        this.origTopicList = topics;
    }
    @Override
    public int getCount() {
        return topics.size();
    }

    @Override
    public TopicItem getItem(int position) {
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
        TextView name;
        TextView description;
        Button learn_button;
        Button quiz_button;
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
            viewItem = inflater.inflate(R.layout.topiclist_list_item, null);
            // Now we can fill the layout with the right values
            TextView name = (TextView) viewItem.findViewById(R.id.topiclist_name);
            TextView description = (TextView) viewItem.findViewById(R.id.topiclist_summary);
            Button learn_button = (Button) viewItem.findViewById(R.id.topiclist_more);
            Button quiz_button = (Button) viewItem.findViewById(R.id.topiclist_quiz);

            holder.name = name;
            holder.description = description;
            holder.learn_button = learn_button;
            holder.quiz_button = quiz_button;

            viewItem.setTag(holder);
        }
        else
            holder = (Holder) viewItem.getTag();

        final TopicItem topic = topics.get(position);
        holder.name.setText(topic.getName());
        holder.description.setText(topic.getDescription());

        viewItem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(context, "You Clicked "+ topic.getName(), Toast.LENGTH_LONG).show();
            }
        });

        return viewItem;
    }

    /*
	 * We create our filter
	 */
    @Override
    public Filter getFilter() {
        if (planetFilter == null)
            planetFilter = new TopicsFilter();

        return planetFilter;
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
                List<TopicItem> nPlanetList = new ArrayList<TopicItem>();

                for (TopicItem topic : topics) {
                    if (topic.getName().toUpperCase().startsWith(constraint.toString().toUpperCase()))
                        nPlanetList.add(topic);
                }

                results.values = nPlanetList;
                results.count = nPlanetList.size();

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
                topics = (List<TopicItem>) results.values;
                notifyDataSetChanged();
            }

        }

    }

}