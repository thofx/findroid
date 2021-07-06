package dev.jdtech.jellyfin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import dev.jdtech.jellyfin.adapters.EpisodeListAdapter
import dev.jdtech.jellyfin.databinding.FragmentSeasonBinding
import dev.jdtech.jellyfin.viewmodels.SeasonViewModel
import dev.jdtech.jellyfin.viewmodels.SeasonViewModelFactory
import org.jellyfin.sdk.model.api.BaseItemDto

@AndroidEntryPoint
class SeasonFragment : Fragment() {

    private lateinit var binding: FragmentSeasonBinding

    private val args: SeasonFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSeasonBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModelFactory = SeasonViewModelFactory(
            requireNotNull(this.activity).application,
            args.seriesId,
            args.seasonId
        )
        val viewModel: SeasonViewModel by viewModels { viewModelFactory }
        binding.viewModel = viewModel
        binding.episodesRecyclerView.adapter =
            EpisodeListAdapter(EpisodeListAdapter.OnClickListener { episode ->
                navigateToEpisodeBottomSheetFragment(episode)
            })
        binding.seriesName.text = args.seriesName
        binding.seasonName.text = args.seasonName
        binding.seriesId = args.seriesId
        binding.seasonId = args.seasonId
    }

    private fun navigateToEpisodeBottomSheetFragment(episode: BaseItemDto) {
        findNavController().navigate(
            SeasonFragmentDirections.actionSeasonFragmentToEpisodeBottomSheetFragment(
                episode.id
            )
        )
    }
}