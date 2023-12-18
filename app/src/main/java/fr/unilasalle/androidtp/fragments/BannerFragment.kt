package fr.unilasalle.androidtp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import fr.unilasalle.androidtp.Activities.BuyHistoryActivity
import fr.unilasalle.androidtp.Activities.MainActivity
import fr.unilasalle.androidtp.Activities.PanierActivity
import fr.unilasalle.androidtp.databinding.ActivityMainBinding
import fr.unilasalle.androidtp.databinding.FragmentBannerBinding


class BannerFragment : Fragment() {

    private lateinit var binding: FragmentBannerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBannerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Logique pour le clic sur l'icône du panier (ouverture de Panier)
        binding.cartImageView.setOnClickListener {
            navigateToPanierActivity()
        }

        // Logique pour le clic sur le logo (retour à l'accueil)
        binding.logoImageView.setOnClickListener {
            navigateToMainActivity()
        }

        binding.accountImageView.setOnClickListener {
            navigateToBuyHistoryActivity()
        }
    }

    private fun navigateToPanierActivity() {
        // TODO Verif si la page est déjà ouverte ou desactiver bouton
        if (activity is PanierActivity) {
            return
        }else{
            // Intent pour démarrer PanierActivity
            val intent = Intent(activity, PanierActivity::class.java)
            startActivity(intent)
        }

    }


    private fun navigateToMainActivity() {
        // Si on est dans l'activité MainActivity, on ne fait rien
        if (activity is MainActivity) {
            return
        }else{
            activity?.finish()
        }
    }

    private fun navigateToBuyHistoryActivity() {
        if (activity is MainActivity) {
            val intent = Intent(activity, BuyHistoryActivity::class.java)
            startActivity(intent)
        }else if(activity is BuyHistoryActivity) {
            return
        }else{
            activity?.finish()
            val intent = Intent(activity, BuyHistoryActivity::class.java)
            startActivity(intent)
        }

    }
}

