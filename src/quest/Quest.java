package quest;

public class Quest {

    String title;
    String body;
    QuestReward questReward;

    public Quest(String title, String body){
        this.title = title;
        this.body = body;
    }

    public Quest(String title, String body, QuestReward questReward){
        this.title = title;
        this.body = body;
        this.questReward = questReward;
    }

}
