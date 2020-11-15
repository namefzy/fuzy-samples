package com.fuzy.example.chain;

/**
 * @ClassName Handler
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/15 17:14
 * @Version 1.0.0
 */
public abstract class Handler {

    private Handler nextHandler;

    public final Response handlerMessage(Request request){
        Response response = null;
        if(this.getHandlerLevel().equals(request.getRequestLevel())){
            response = this.echo(request);
        }else{
            if(this.nextHandler!=null){
                response = this.nextHandler.handlerMessage(request);
            }else{
                //没有适当的处理者，业务自行处理
            }
        }
        return response;
    }

    public void setNext(Handler handler){
        this.nextHandler = handler;
    }

    protected abstract Level getHandlerLevel();

    protected abstract Response echo(Request request);
}
