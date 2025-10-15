import models.CompanyInfo;
import models.ProfileInfo;
import models.UserInfo;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Ex1 {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        //ProfileInfo profileInfo = getProfileInfo(1L); //1ый вариант
        ProfileInfo profileInfo = getProfileInfo2(1L); //2ой вариант

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("Execution time: " + duration + " ms");
    }

    //1 вариант
    public static ProfileInfo getProfileInfo(Long id) {
        FutureTask<CompanyInfo> futureTask = new FutureTask<>(
                () -> getCompanyInfo(id));

        Thread thread = new Thread(futureTask);
        thread.start();
        UserInfo userInfo = getUserInfo(id);
        try {
            CompanyInfo companyInfo = futureTask.get();
            return new ProfileInfo(userInfo, companyInfo);
        }
        catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    //2 вариант
    public static ProfileInfo getProfileInfo2(Long id)  {
        CompletableFuture<UserInfo> userInfoFuture = CompletableFuture.supplyAsync(() -> getUserInfo(id));
        CompletableFuture<CompanyInfo> companyInfoFuture = CompletableFuture.supplyAsync(() -> getCompanyInfo(id));

        CompletableFuture<Void> allFutures = CompletableFuture.allOf(userInfoFuture, companyInfoFuture);
        try {
            allFutures.get();
            return new ProfileInfo(userInfoFuture.get(), companyInfoFuture.get());
        }
        catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

    }

    private static UserInfo getUserInfo(Long id) {
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
        return new UserInfo(id, "User1", "20");
    }

    private static CompanyInfo getCompanyInfo(Long id) {
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return new CompanyInfo(id, "Company 1");
    }
}
