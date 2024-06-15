package co.edu.poli.ces3.universitas.dao;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Coments {
   private String mail;
    private String fullName;
    private String user_id;
    private String comment_text;
    private Date timestamp;
    private Replies replies[];

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getComment_text() {
        return comment_text;
    }

    public void setComment_text(String comment_text) {
        this.comment_text = comment_text;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Replies[] getReplies() {
        return replies;
    }

    public void setReplies(Replies[] replies) {
        this.replies = replies;
    }

    public Coments() {

    }
    public boolean  insertarComents(){
        try {
            FileInputStream serviceAccount = null;
            serviceAccount = new FileInputStream("C:\\Users\\juan0\\Downloads\\firebase.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    //.setDatabaseUrl("https://omesaprueba1.firebaseio.com")
                    .build();

            FirebaseApp.initializeApp(options);

            Firestore db = FirestoreClient.getFirestore();

            DocumentReference docRef = db.collection("comments").document("alovelace");

            Map<String, Object> data = new HashMap<>();
            data.put("mail", this.getMail());
            data.put("fullName", this.getFullName());
            data.put("user_id", this.getUser_id());
            data.put("comment_text", this.getComment_text());
            data.put("timestamp", this.getTimestamp());
            Gson gson = new Gson();

            // Convertir el objeto Persona a JSON como un String
            String jsonReplies = gson.toJson(this.getReplies());

            data.put("replies", jsonReplies);

            ApiFuture<WriteResult> result = docRef.set(data);

            System.out.println("Update time : " + result.get().getUpdateTime());


            System.out.println("Se conecto a firebase sin problemas");
            return true;
        } catch (IOException | InterruptedException | ExecutionException e) {
            System.out.println("Update time : ");
            throw new RuntimeException(e);
        }
    }
}
