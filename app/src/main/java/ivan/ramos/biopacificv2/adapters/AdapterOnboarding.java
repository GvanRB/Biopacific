package ivan.ramos.biopacificv2.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ivan.ramos.biopacificv2.R;
import ivan.ramos.biopacificv2.models.onboardingModel;

public class AdapterOnboarding extends RecyclerView.Adapter<AdapterOnboarding.OnboardingViewHolder>{
    private List<onboardingModel> onboardingModels;

    public AdapterOnboarding(List<onboardingModel> onboardingModels) {
        this.onboardingModels = onboardingModels;
    }

    @NonNull
    @Override
    public OnboardingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OnboardingViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_onboarding,parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull OnboardingViewHolder holder, int position) {
        holder.setOnboardingData(onboardingModels.get(position));
    }

    @Override
    public int getItemCount() {
        return onboardingModels.size();
    }

    class OnboardingViewHolder extends RecyclerView.ViewHolder{
        private TextView textTitle;
        private TextView textDescrip;
        private ImageView imageOnboarding;

        public OnboardingViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            textDescrip = itemView.findViewById(R.id.textDescp);
            imageOnboarding = itemView.findViewById(R.id.imageOnboarding);
        }
        void setOnboardingData(onboardingModel model){
            textTitle.setText(model.getTitle());
            textDescrip.setText(model.getDescrip());
            imageOnboarding.setImageResource(model.getImage());
        }
    }
}
