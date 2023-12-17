package fr.unilasalle.androidtp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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

        /* @TODO : A décommenter pour afficher le badge du panier
        // S'il y a un objet dans le panier, on affiche le badge
        if (ShoppingCart.cartItems.isNotEmpty()) {
            binding.cartNotificationImageView.visibility = View.VISIBLE
        } else {
            binding.cartNotificationImageView.visibility = View.INVISIBLE
        }

         */
    }

    // Méthode qui met à jour le badge du panier dès que


    private fun navigateToPanierActivity() {
        // TODO Verif si la page est déjà ouverte ou desactiver bouton
        // Intent pour démarrer PanierActivity
        val intent = Intent(activity, PanierActivity::class.java)
        startActivity(intent)
    }



    private fun navigateToMainActivity() {
        activity?.finish() // On ferme l'activité actuelle
    }
}

