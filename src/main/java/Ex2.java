import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Ex2 {
    public static void main(String[] args) {
        List<Profile> profiles = new ArrayList<>();
        profiles.add(new Profile(1L,0L,1L));
        profiles.add(new Profile(2L,0L,1L));
        profiles.add(new Profile(3L,0L,2L));
        profiles.add(new Profile(4L,1L,1L));
        profiles.add(new Profile(5L,1L,2L));

        Map<Long, Map<Long, List<Profile>>> res = groupByOrgIdAndGroupId(profiles);

        System.out.println("{");
        res.forEach((key, value) -> {
            System.out.println("    \""+key+"\": {");
            value.forEach((key1, value1) -> {
                System.out.print("        \""+key1 + "\" : [" );
                value1.forEach(profile -> System.out.print("{" + profile.id + "},"));
                System.out.println("]");
            });
            System.out.println("}");
        });
        System.out.println("}");


    }
    public static Map<Long, Map<Long, List<Profile>>> groupByOrgIdAndGroupId(List<Profile> data) {
        return data.stream()
                .collect(Collectors.groupingBy(
                        profile -> profile.orgId,
                        Collectors.groupingBy(
                                profile -> profile.groupId
                        )
                ));
    }
}

class Profile {
    public Long id;
    public Long orgId;
    public Long groupId;

    public Profile(Long id, Long orgId, Long groupId) {
        this.id = id;
        this.orgId = orgId;
        this.groupId = groupId;
    }
}