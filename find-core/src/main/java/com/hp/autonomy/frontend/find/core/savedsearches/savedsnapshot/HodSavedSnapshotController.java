package com.hp.autonomy.frontend.find.core.savedsearches.savedsnapshot;

import com.hp.autonomy.aci.content.fieldtext.FieldText;
import com.hp.autonomy.aci.content.fieldtext.FieldTextBuilder;
import com.hp.autonomy.aci.content.fieldtext.MATCH;
import com.hp.autonomy.frontend.find.core.savedsearches.EmbeddableIndex;
import com.hp.autonomy.frontend.find.core.savedsearches.FieldAndValue;
import com.hp.autonomy.frontend.find.core.search.QueryRestrictionsBuilder;
import com.hp.autonomy.hod.client.api.resource.ResourceIdentifier;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.searchcomponents.core.search.DocumentsService;
import com.hp.autonomy.searchcomponents.hod.search.HodQueryRestrictions;
import com.hp.autonomy.searchcomponents.hod.search.HodSearchResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping(SavedSnapshotController.PATH)
public class HodSavedSnapshotController extends SavedSnapshotController<ResourceIdentifier, HodSearchResult, HodErrorException> {
    public HodSavedSnapshotController(SavedSnapshotService service, DocumentsService<ResourceIdentifier, HodSearchResult, HodErrorException> documentsService) {
        super(service, documentsService);
    }

    private List<ResourceIdentifier> getDatabases(Set<EmbeddableIndex> indexes) {
        List<ResourceIdentifier> databases = new ArrayList<>();

        for(EmbeddableIndex index: indexes) {
            databases.add(new ResourceIdentifier(index.getDomain(), index.getName()));
        }

        return databases;
    }

    @Override
    protected String getStateToken(final SavedSnapshot snapshot) throws Exception {
        HodQueryRestrictions.Builder queryRestrictionsBuilder = new HodQueryRestrictions.Builder()
                .setDatabases(this.getDatabases(snapshot.getIndexes()))
                .setQueryText(this.getQueryText(snapshot)).setFieldText(this.getFieldText(snapshot.getParametricValues()))
                .setMaxDate(snapshot.getMaxDate())
                .setMinDate(snapshot.getMinDate());

        return documentsService.getStateToken(queryRestrictionsBuilder.build(), Integer.MAX_VALUE);
    }
}
