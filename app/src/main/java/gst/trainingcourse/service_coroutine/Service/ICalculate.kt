package gst.trainingcourse.service_coroutine.Service

interface ICalculate {
    fun Calculate(onFinish: (Long) ->Unit)
}