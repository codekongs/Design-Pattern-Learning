package codekong;

/**
 * Created by 尚振鸿 on 17-12-2. 21:27
 * mail:szh@codekong.cn
 */

public interface IUserInfo {
    void getId();
    void setId(int id);
    void getName();
    void setName(String name);
    void getPassword();
    void setPassword(String password);

    void addUser(int id, String name, String password);
    void deleteUser(int id);
}
