package food.cliente.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import food.cliente.PreguntasActivity;
import food.cliente.R;
import food.cliente.entity.AcercaDe;

/**
 * By: El Bryant
 */

public class AcercaDeAdapter   extends RecyclerView.Adapter<AcercaDeAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<AcercaDe> mAcercaDe;

    public AcercaDeAdapter(Context context, ArrayList<AcercaDe> acercaDe) {
        mContext = context;
        mAcercaDe = acercaDe;
    }

    @NonNull
    @Override
    public AcercaDeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.card_acerca_de, parent, false);
        return new AcercaDeAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final AcercaDeAdapter.ViewHolder holder, int position) {
        AcercaDe acercaDe = mAcercaDe.get(position);
        final String idAcercaDe = acercaDe.getIdAcercaDe();
        final String titulo = acercaDe.getTitulo();
        final String contenido = acercaDe.getContenido();
        holder.tvIdAcercaDe.setText(idAcercaDe);
        holder.tvTitulo.setText(titulo);
        holder.cvAcercaDe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreguntasActivity.cvContenido.setVisibility(View.VISIBLE);
                PreguntasActivity.tvTituloCard.setText(titulo);
                PreguntasActivity.tvContenidoCard.setText(contenido);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAcercaDe.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvIdAcercaDe, tvTitulo;
        private CardView cvAcercaDe;

        public ViewHolder(@NonNull View v) {
            super(v);
            tvIdAcercaDe = (TextView) v.findViewById(R.id.tvIdAcercaDe);
            tvTitulo = (TextView) v.findViewById(R.id.tvTitulo);
            cvAcercaDe = (CardView) v.findViewById(R.id.cvAcercaDe);
        }
    }
}