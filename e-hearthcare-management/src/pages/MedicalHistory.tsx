import React from 'react';
import { Header } from '../components/Header';
import { GridComponent, ColumnsDirective, ColumnDirective, Page, Selection,
  Inject, Edit, Toolbar, Sort, Filter, CommandColumn, ToolbarItems, SearchSettingsModel
} from '@syncfusion/ej2-react-grids';
import { DataManager, WebApiAdaptor, WebMethodAdaptor } from '@syncfusion/ej2-data';
import { selectionsettings, editing, commands } from '../utils/DataBindingUtil';

export const MedicalHistory = () => {
  const dataManager: DataManager = new DataManager({
    adaptor: new WebApiAdaptor(),
    url: "http://192.168.64.2:30479/ehealthcare/v1/medicshistory",
  });

  const toolbarOptions: ToolbarItems[] = ['Add', 'Search'];
  const searchOptions: SearchSettingsModel = {
    fields: ['patientId']
  };

  return(
    <div className="m-2 md:m-10 mt-24 p-2 md:p-10 bg-white rounded-3xl">
      <Header category="Patients" title="Medical History" />
      <GridComponent
        dataSource={dataManager}
        enableHover={false}
        allowPaging
        pageSettings={{ pageCount: 5 }}
        selectionSettings={selectionsettings}
        toolbar={toolbarOptions}
        editSettings={editing}
        allowSorting
        searchSettings={searchOptions}
      >
        <ColumnsDirective>
          <ColumnDirective field='id' headerText='ID' width='50' textAlign='Center' isPrimaryKey={true} />
          <ColumnDirective field='patientId' headerText='Patient ID' width='100' textAlign='Center' /> 
          <ColumnDirective field='bloodType' headerText='Blood Type' width='100' textAlign='Center' />
          <ColumnDirective field='bloodSugar' headerText='Blood Sugar' width='100' textAlign='Center' />
          <ColumnDirective field='bloodPressure' headerText='Blood Pressure' width='100' textAlign='Center' />
          <ColumnDirective field='weight' headerText='Weight' width='100' textAlign='Center' />
          <ColumnDirective field='notes' headerText='Notes' width='100' textAlign='Center' />
          <ColumnDirective headerText='Commands' width='120' commands={commands} />
        </ColumnsDirective>
        <Inject services={[Page, Selection, Toolbar, Edit, Sort, Filter, CommandColumn]} />
      </GridComponent>
    </div>
  );
};
