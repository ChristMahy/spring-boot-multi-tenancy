package be.cmahy.multitenantmysql.configthird;

public class ThreadLocalStorage {
    private static final ThreadLocal<String> tenant = new ThreadLocal<>();

    public static void setTenantName(String tenantName) {
        tenant.set(tenantName);
    }

    public static String getTenantName() {
        return tenant.get();
    }
}
