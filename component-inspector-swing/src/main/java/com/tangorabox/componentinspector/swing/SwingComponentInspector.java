package com.tangorabox.componentinspector.swing;

import com.tangorabox.componentinspector.core.AbstractComponentInspector;
import com.tangorabox.componentinspector.core.CSSStyleClass;
import com.tangorabox.componentinspector.core.ComponentDetails;
import com.tangorabox.componentinspector.core.ObjectMetadataExtractor;
import com.tangorabox.componentinspector.swing.styling.SwingCSSStyleDecorator;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SwingComponentInspector extends AbstractComponentInspector<Component> {

    private final SwingCSSStyleDecorator cssStyleDecorator = new SwingCSSStyleDecorator();

    @Override
    protected Component getParent(Component component) {
        if (component == null) {
            return null;
        }
        return component.getParent();
    }

    @Override
    protected Component createFieldNameComponent(Component component) {
        String fieldName = ObjectMetadataExtractor.getDeclaredFieldNameInParent(component, this);
        if (fieldName == null) {
            return null;
        }
        JLabel result = new JLabel(fieldName);
        return cssStyleDecorator.decorate(result, CSSStyleClass.FIELD_COMPONENT);
    }

    @Override
    protected Component createClassComponent(Component component) {
        JLabel result = new JLabel(component.getClass().getName());
        return cssStyleDecorator.decorate(result, CSSStyleClass.CLASS_COMPONENT);
    }

    @Override
    protected Component createStylesComponent(Component component) {
        return null;
    }

    @Override
    protected Component createComponentDetailsPanel(ComponentDetails<Component> details) {
        JPanel jPanel = new JPanel(new FlowLayout());
        jPanel.add(details.getClassComponent());
        if (details.getFieldNameComponent() != null) {
            jPanel.add(details.getFieldNameComponent());
        }

        Component result = cssStyleDecorator.decorate(jPanel, CSSStyleClass.COMPONENT_DETAILS);
        result.setBounds(details.getLocationX(), details.getLocationY(),
                result.getPreferredSize().width, result.getPreferredSize().height);
        return result;
    }

    @Override
    protected void buildCascade(List<ComponentDetails<Component>> hierarchy) {
        for (int componentLevel = 0, componentIndex = hierarchy.size() - 1; componentIndex >= 0; componentIndex--, componentLevel++) {
            ComponentDetails<Component> componentChild = hierarchy.get(componentIndex);
            componentChild.setLocation(componentIndex * HORIZONTAL_SPACING, componentLevel * VERTICAL_SPACING);
        }
    }
}
