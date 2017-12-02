package codekong;

/**
 * Created by 尚振鸿 on 17-12-2. 21:52
 * mail:szh@codekong.cn
 */

public interface IUserBiz {
    void addUser(int id, String name, String password);
    void deleteUser(int id);
}
