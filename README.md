### Решение первой задачи в классе Ex1, два варианта
```java
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
```

### Решение второй задачи в классе Ex2    
```java
    public static Map<Long, Map<Long, List<Profile>>> groupByOrgIdAndGroupId(List<Profile> data) {
        return data.stream()
                .collect(Collectors.groupingBy(
                        profile -> profile.orgId,
                        Collectors.groupingBy(
                                profile -> profile.groupId
                        )
                ));
    }
```
