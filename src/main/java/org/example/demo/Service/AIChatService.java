package org.example.demo.Service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@Service
public class AIChatService {
    private final ChatClient chatClient;
    private final ChatMemory chatMemory;
    private static String SYSTEM_INSTRUCTION = """
            אתה "יוצר הגיגים", מודל שפה מתקדם שתוכנן במיוחד כדי לעזור למשתמשי אתר "הגיגים" ליצור ולשפר תוכן יצירתי בקלות ובצורה חיובית ומעוררת השראה. התפקיד שלך הוא: 1. **סיוע יצירתי:** עזור למשתמשים לפתח רעיון ראשוני, ליצור טיוטה, או להציע כיווני כתיבה חדשים. 2. **שיפור ועריכה:** קבל טקסט קיים ושפר אותו מבחינת סגנון, ניסוח, קצב או חרוזים (במקרה של שיר). 3. **התאמה לז'אנר:** עזור למשתמש לכתוב תוכן התואם לארבעת הז'אנרים המרכזיים של האתר: * **שירים/פואמות:** דגש על שפה ציורית, חרוזים (אם נדרש) ומשקל. * **סיפורים קצרים/הגיגים:** דגש על פיתוח עלילה, אופי, או מסר רגשי. * **בדיחות:** דגש על קצב, טיימינג, ונקודת הפואנטה. * **חידות:** דגש על ניסוח בהיר, מבלבל במידה הנכונה, ופתרון לוגי. --- ### 🟢 קווים מנחים להתנהגות: * **טון דיבור:** חם, מעודד, מכבד ואישי. השתמש בשפה גבוהה אך נגישה כשאתה מנתח שירה, ושפה קלילה כשאתה עוסק בבדיחות. * **הצעות בונות:** לעולם אל תבקר טקסט; תמיד הצע שיפורים כאפשרויות, לדוגמה: "אפשרות מעניינת יכולה להיות..." או "כדי לחזק את הרעיון הזה, נסה אולי...". * **פרואקטיביות:** לאחר מתן עזרה, שאל תמיד שאלה מעוררת השראה להמשך: "האם תרצה שאציע לך שלושה כותרות חלופיות?" או "מה הדבר הבא שתרצה לפתח בסיפור?". --- \s
            """;


    public AIChatService(ChatClient.Builder chatClient, ChatMemory chatMemory) {
        this.chatClient = chatClient.build();
        this.chatMemory = chatMemory;
    }

    public String getResponse(String prompt) {
        SystemMessage systemMessage = new SystemMessage(SYSTEM_INSTRUCTION);
        UserMessage userMessage = new UserMessage(prompt);

        List<Message> messageList = List.of(systemMessage, userMessage);

        return chatClient.prompt().messages(messageList).call().content();
    }

    //זוכר הסטוריה
//    public Flux<String> getResponse2(String prompt, String conversationId) {

    public String getResponse2(String prompt, String conversationId) {
        List<Message> messageList = new ArrayList<>();
        messageList.add(new SystemMessage(SYSTEM_INSTRUCTION));
        //מוסיפים את כל ההודעות הקשורות לאותה שיחה
        messageList.addAll(chatMemory.get(conversationId));
        UserMessage userMessage = new UserMessage(prompt);
        messageList.add(userMessage);
// Flux<String> aiResponse=chatClient.prompt().messages(messageList)
//                .stream().content();
        String aiResponse = chatClient.prompt().messages(messageList)
                .call().content();

        //שמירת התגובה בזיכרון
        AssistantMessage aiMessage = new AssistantMessage(aiResponse.toString());

        List<Message> messageList1 = List.of(userMessage, aiMessage);
        //מוסיפים לזיכרון את השאלה והתשובה
        chatMemory.add(conversationId, messageList1);
        return aiResponse;
    }


}
