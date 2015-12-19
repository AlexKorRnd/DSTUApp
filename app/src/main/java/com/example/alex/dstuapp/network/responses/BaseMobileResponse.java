package com.example.alex.dstuapp.network.responses;


/* второй вид базового ответа сервера
   (код ошибки сразу в теле ответа)
 */
public class BaseMobileResponse {
    private String mobileErr;

    public String getMobileErr() {
        return mobileErr;
    }

}
