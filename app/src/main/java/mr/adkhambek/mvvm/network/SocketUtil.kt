package mr.adkhambek.mvvm.network

import android.util.Log
import androidx.lifecycle.LiveData
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.Socket
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import javax.inject.Inject


class SocketUtil @Inject constructor(
    private val gson: Gson,
    private val socketIO: Socket,
) {

    fun connect() {
        socketIO.connect()
    }

    fun disconnect() {
        socketIO.disconnect()
    }

    inner class EventLiveData<T>(private val clazz: Class<T>, private val event: String) :
        LiveData<T>(), Emitter.Listener {

        override fun call(vararg args: Any?) {
            val argument: String = args[0].toString()
            Log.i("SOCKET_IO", argument)
            try {
                val obj: T = gson.fromJson<T>(argument, clazz)
                postValue(obj)
            } catch (e: JsonSyntaxException) {
                Log.e("SOCKET_IO", e.message ?: e.localizedMessage ?: "Error")
            } catch (e: Exception) {
                Log.e("SOCKET_IO", e.message ?: e.localizedMessage ?: "Error")
            }
        }

        init {
            socketIO.on(event, this)

            socketIO.on("connect") {
                Log.d("SOCKET_IO", "Connect")
            }

            socketIO.on("disconnect") {
                Log.d("SOCKET_IO", "disconnect")
            }
        }

        override fun onInactive() {
            socketIO.off(event, this)
            super.onInactive()
        }
    }
}