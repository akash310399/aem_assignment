package com.training.aem.core.models.impl;

import com.adobe.granite.ui.components.rendercondition.RenderCondition;
import com.adobe.granite.ui.components.rendercondition.SimpleRenderCondition;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.commons.lang3.StringUtils;
import org.apache.jackrabbit.api.security.user.Authorizable;
import org.apache.jackrabbit.api.security.user.Group;
import org.apache.jackrabbit.api.security.user.UserManager;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import javax.jcr.RepositoryException;
import java.lang.reflect.Field;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
@MockitoSettings(strictness = Strictness.LENIENT)
class UserGroupRenderConditionImplTest {

    AemContext ctx = new AemContext();

    @InjectMocks
    UserGroupRenderConditionImpl userGroupRenderCondition;

    @Mock
    UserManager userManager;

    @Mock
    SlingHttpServletRequest request;

    @Mock
    ResourceResolver resourceResolver;

    @Mock
    private Authorizable currentUser;

    @Mock
    private Iterator<Group> groupIterator;



    @Mock
    private Group userGroup;

    @BeforeEach
    void setUp() {
        ctx.addModelsForClasses(UserGroupRenderConditionImpl.class);
    }

    @Test
    void init() throws RepositoryException, IllegalAccessException, NoSuchFieldException {

        String groupId = "groupId";

        when(resourceResolver.adaptTo(UserManager.class)).thenReturn(userManager);

        when(resourceResolver.getUserID()).thenReturn("userId");

        // Mocking the behavior of the UserManager
        when(userManager.getAuthorizable("userId")).thenReturn(currentUser);
        when(currentUser.memberOf()).thenReturn(groupIterator);

        // Mocking the behavior of the Iterator
        when(groupIterator.hasNext()).thenReturn(true, false);
        when(groupIterator.next()).thenReturn(userGroup);

        // Mocking the behavior of the Group
        when(userGroup.getID()).thenReturn(groupId);

        Field field = UserGroupRenderConditionImpl.class.getDeclaredField("group");
        field.setAccessible(true);
        field.set(userGroupRenderCondition, "groupId");


        userGroupRenderCondition.init();


    }
}