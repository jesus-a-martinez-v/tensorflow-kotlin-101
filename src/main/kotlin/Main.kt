import org.tensorflow.Graph
import org.tensorflow.Session
import org.tensorflow.Tensor
import org.tensorflow.TensorFlow

fun main(args: Array<String>) {
    try {
        val graph = Graph()
        val message = "Hello from ${TensorFlow.version()}"

        // Creates a tensor with the given content.
        val tensor = Tensor.create(message.toByteArray(Charsets.UTF_8))
        // TensorFlow for Java doesn't support convenient methods for the different types of operations,
        // so we must provide the operation name, along with a reference name. We also need to set attributes
        // using strings, as seen in the usage of the setAttr() method.
        graph.opBuilder("Const", "MyConst")
                .setAttr("dtype", tensor.dataType())
                .setAttr("value", tensor)
                .build()

        // Let's create a session using our graph and then execute it. With the fetch() method we provide
        // the name of the tensor that'll hold the result we're interested in.
        val session = Session(graph)
        val output = session
                .runner()
                .fetch("MyConst")
                .run()[0]

        println(String(output.bytesValue(), Charsets.UTF_8))
    } catch (e: Exception) {
        println("Something happened ${e.localizedMessage}")
    }
}