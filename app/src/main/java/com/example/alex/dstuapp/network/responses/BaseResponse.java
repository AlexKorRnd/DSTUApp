package com.example.alex.dstuapp.network.responses;


/* первый вид базового ответа сервера
   (код ошибки содержится в объекте menu)
 */
public class BaseResponse {
    private boolean success;
    private Menu menu;

    public boolean isSuccess() {
        return success;
    }

    public String getMobileErr() {
        return menu.mobileErr;
    }

    private static class Menu {
        private String mobileErr;
    }

}
