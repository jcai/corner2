/*
 * Created on 2004-10-24
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package corner.orm.lucene.xerdoc.xdmf.lucene;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Winters.J.Mi
 * 
 * SimpleWordIdentifier just implements the Simple Maximum Matching Algorithm.
 */
public class SimpleWordIdentifier extends WordIdentifier {

    public SimpleWordIdentifier(AbstractDictionary dic) {
        super(dic);
    }

    public String[] identifyWord(String content) {
        List segments = new ArrayList();
        int nEnd = 1;
        int nBegin = 0;
        String key = content.substring(nBegin, nBegin + 1);
        String candidate = "";
        while (nEnd < content.length()) {
            candidate += content.charAt(nEnd);
            if (dictionary_.maybeHaveEntry(key + candidate)) {
                ++nEnd;
            } else {
                String segment = key
                        + candidate.substring(0, candidate.length() - 1);
                segments.add(segment);

                nBegin = nEnd;
                nEnd = nBegin + 1;
                candidate = "";
                key = content.substring(nBegin, nBegin + 1);
            }
        }
        segments.add(new String(key + candidate));

        String[] result = new String[segments.size()];
        segments.toArray(result);

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; ++i)
            sb.append(result[i] + "  ");
        System.out.println(sb.toString());

        return result;
    }
}
