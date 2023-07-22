package dev.jdtech.jellyfin.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dev.jdtech.jellyfin.player.video.R
import dev.jdtech.jellyfin.viewmodels.PlayerActivityViewModel
import java.lang.IllegalStateException

class SpeedSelectionDialogFragment(
    private val viewModel: PlayerActivityViewModel,
) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val speedTexts = listOf("0.5x", "1x", "1.5x", "2x", "3x")
        val speedNumbers = listOf(0.5f, 1f, 1.5f, 2f, 3f)

        return activity?.let { activity ->
            val builder = MaterialAlertDialogBuilder(activity)
            builder.setTitle(getString(R.string.select_playback_speed))
                .setSingleChoiceItems(
                    speedTexts.toTypedArray(),
                    speedNumbers.indexOf(viewModel.playbackSpeed),
                ) { dialog, which ->
                    viewModel.selectSpeed(
                        speedNumbers[which],
                    )
                    dialog.dismiss()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
