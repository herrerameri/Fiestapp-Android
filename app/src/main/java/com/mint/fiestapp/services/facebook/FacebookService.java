package com.mint.fiestapp.services.facebook;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.mint.fiestapp.models.entidades.UsuarioModel;
import com.mint.fiestapp.services.IService;

import org.json.JSONException;
import org.json.JSONObject;

public class FacebookService implements IFacebookService {
    public interface IFacebookServiceCallback{
        void callbackObtenerNombre(UsuarioModel respuesta);
    }
    IFacebookServiceCallback listener;

    public FacebookService(IFacebookServiceCallback listener){
        this.listener = listener;
    }

    public void getDisplayName(final String facebookId){
        GraphRequest request = GraphRequest.newGraphPathRequest(
                AccessToken.getCurrentAccessToken(), facebookId,
                new GraphRequest.Callback()
                {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        try {
                            if(response.getError() == null){
                                String nombre = response.getJSONObject().getString("name");
                                UsuarioModel usuario = new UsuarioModel(facebookId, nombre);
                                listener.callbackObtenerNombre(usuario);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        request.executeAsync();
    }
}
