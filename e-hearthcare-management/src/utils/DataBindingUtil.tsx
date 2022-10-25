import React from 'react';
import { ToolbarItems, EditSettingsModel, CommandModel } from '@syncfusion/ej2-react-grids';
import { DataManager, WebApiAdaptor } from '@syncfusion/ej2-data';

export const selectionsettings = { persistSelection: true};
export const toolbarOptions: ToolbarItems[] = ['Add'];
export const editing: EditSettingsModel = { allowAdding: true, allowEditing: true, allowDeleting: true, mode: 'Dialog' };

export const commands:CommandModel[] = [
  {type: 'Edit', buttonOption: { cssClass: 'e-flat', iconCss: 'e-edit e-icons'}},
  {type: 'Delete', buttonOption: { cssClass: 'e-flat', iconCss: 'e-delete e-icons'}},
  {type: 'Save', buttonOption: { cssClass: 'e-flat', iconCss: 'e-update e-icons'}},
  {type: 'Cancel', buttonOption: { cssClass: 'e-flat', iconCss: 'e-cancel-icon e-icons'}},
];


