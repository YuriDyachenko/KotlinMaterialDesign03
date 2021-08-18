package dyachenko.kotlinmaterialdesign03.viewmodel.notes

import dyachenko.kotlinmaterialdesign03.model.notes.Note
import dyachenko.kotlinmaterialdesign03.viewmodel.AppState
import dyachenko.kotlinmaterialdesign03.viewmodel.CommonViewModel
import kotlinx.coroutines.*

class NotesViewModel : CommonViewModel(), CoroutineScope by MainScope() {

    fun getNotesFromServer() {
        data.value = AppState.Loading
        launch {
            val job: Deferred<List<Note>> = async(Dispatchers.IO) { Note.getDefValues() }
            data.value = AppState.Success(job.await())
        }
    }
}