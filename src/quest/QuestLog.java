package quest;

import quest.Quest;

public class QuestLog {

    private static final int QUEST_LOG_SIZE = 25;

    private Quest[] questList;

    public QuestLog(){
        questList = new Quest[15];
    }

    public void acceptQuest(Quest quest){
        for(int i = 0; i < QUEST_LOG_SIZE; i++){
            if(questList[i] == null){
                questList[i] = quest;
                break;
            }
        }
    }

    public void abandonQuest(Quest quest){
        for(int i = 0; i < QUEST_LOG_SIZE; i++){
            if(questList[i] == quest){
                questList = null;
                break;
            }
        }
    }

    public Quest[] getQuestList() {
        return questList;
    }

}
