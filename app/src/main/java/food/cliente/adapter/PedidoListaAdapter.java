package food.cliente.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import java.util.ArrayList;
import food.cliente.R;
import food.cliente.entity.PedidoLista;
import food.cliente.publico.AppController;

/**
 * By: El Bryant
 */

public class PedidoListaAdapter extends RecyclerView.Adapter<PedidoListaAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<PedidoLista> mPedidoLista;
    private ImageLoader imgLoader = AppController.getInstance().getImageLoader();

    public PedidoListaAdapter(Context context, ArrayList<PedidoLista> pedidoLista) {
        mContext = context;
        mPedidoLista = pedidoLista;
    }

    @NonNull
    @Override
    public PedidoListaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.card_pedido_lista, parent, false);
        return new PedidoListaAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final PedidoListaAdapter.ViewHolder holder, int position) {
        PedidoLista pedidoLista = mPedidoLista.get(position);
        final String idPedidoLista = pedidoLista.getIdPedidoLista();
        final String iconoTienda = pedidoLista.getIconoTienda();
        final String fecha = pedidoLista.getFecha();
        final String hora = pedidoLista.getHora();
        final String nombreProducto = pedidoLista.getNombreProducto();
        final String monto = pedidoLista.getMonto();
        holder.tvIdPedido.setText(idPedidoLista);
        holder.nivIconoTienda.setImageUrl(iconoTienda, imgLoader);
        holder.tvFecha.setText(fecha);
        holder.tvHora.setText(hora);
        holder.tvNombreProducto.setText(nombreProducto);
        holder.tvMonto.setText(monto);
    }

    @Override
    public int getItemCount() {
        return mPedidoLista.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public NetworkImageView nivIconoTienda;
        public TextView tvIdPedido, tvNombreProducto, tvFecha, tvHora, tvMonto;
        private CardView cvPedidoLista;

        public ViewHolder(@NonNull View v) {
            super(v);
            nivIconoTienda = (NetworkImageView) v.findViewById(R.id.nivLogoTienda);
            tvIdPedido = (TextView) v.findViewById(R.id.tvIdPedido);
            tvNombreProducto = (TextView) v.findViewById(R.id.tvNombreProducto);
            tvFecha = (TextView) v.findViewById(R.id.tvFechaPedido);
            tvHora = (TextView) v.findViewById(R.id.tvHoraPedido);
            tvMonto = (TextView) v.findViewById(R.id.tvMonto);
            cvPedidoLista = (CardView) v.findViewById(R.id.cvPedidoLista);
        }
    }
}