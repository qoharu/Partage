package id.innovable.partage.Helper;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import id.innovable.partage.LoginActivity;

/**
 * Created by Qoharu on 2/15/17.
 */

public class googleAuth {
    public void firebaseAuthWithGoogle(GoogleSignInAccount acct, FirebaseAuth mAuth) {
        Log.d("Firebaseauth", "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential);
    }
}
