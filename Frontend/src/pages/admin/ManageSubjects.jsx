import { useState, useEffect } from 'react';
import { Table, Button, Modal, Form, Input, message, Space, Popconfirm, Card, Row, Col } from 'antd';
import { PlusOutlined, EditOutlined, DeleteOutlined } from '@ant-design/icons';
import { adminService } from '../../services/adminService';

const ManageSubjects = () => {
  const [subjects, setSubjects] = useState([]);
  const [loading, setLoading] = useState(false);
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [editingSubject, setEditingSubject] = useState(null);
  const [form] = Form.useForm();

  const fetchSubjects = async () => {
    setLoading(true);
    try {
      const response = await adminService.getSubjects();
      setSubjects(response);
    } catch (err) {
      message.error('Failed to fetch subjects');
      console.error('Error fetching subjects:', err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchSubjects();
  }, []);

  const handleCreate = () => {
    setEditingSubject(null);
    form.resetFields();
    setIsModalVisible(true);
  };

  const handleEdit = (record) => {
    setEditingSubject(record);
    form.setFieldsValue({
      name: record.name,
    });
    setIsModalVisible(true);
  };

  const handleDelete = async (subjectId) => {
    try {
      await adminService.deleteSubject(subjectId);
      message.success('Subject deleted successfully');
      fetchSubjects();
    } catch (error) {
      message.error(error.response?.data?.message || 'Failed to delete subject');
    }
  };

  const handleSubmit = async (values) => {
    try {
      if (editingSubject) {
        await adminService.editSubject(editingSubject.id, values);
        message.success('Subject updated successfully');
      } else {
        await adminService.createSubject(values);
        message.success('Subject created successfully');
      }
      setIsModalVisible(false);
      form.resetFields();
      fetchSubjects();
    } catch (error) {
      message.error(error.response?.data?.message || 'Operation failed');
    }
  };

  const columns = [
    {
      title: 'ID',
      dataIndex: 'id',
      key: 'id',
      width: '10%',
      align: 'center',
    },
    {
      title: 'Subject Name',
      dataIndex: 'name',
      key: 'name',
      width: '25%',
      align: 'center',
    },
    {
      title: 'Course',
      dataIndex: ['course', 'name'],
      key: 'course',
      width: '25%',
      align: 'center',
    },
    {
      title: 'Teacher',
      dataIndex: ['teacher', 'name'],
      key: 'teacher',
      width: '20%',
      align: 'center',
    },
    {
      title: 'Actions',
      key: 'actions',
      width: '20%',
      align: 'center',
      render: (_, record) => (
        <Space>
          <Button
            type="primary"
            icon={<EditOutlined />}
            onClick={() => handleEdit(record)}
            size="small"
          >
            Edit
          </Button>
          <Popconfirm
            title="Are you sure you want to delete this subject?"
            onConfirm={() => handleDelete(record.id)}
            okText="Yes"
            cancelText="No"
          >
            <Button danger icon={<DeleteOutlined />} size="small">
              Delete
            </Button>
          </Popconfirm>
        </Space>
      ),
    },
  ];

  return (
    <div className="h-full flex flex-col" style={{ padding: '1rem' }}>
      <div className="flex justify-between items-center" style={{ marginBottom: '1rem' }}>
        <h1 className="text-2xl font-bold">Manage Subjects</h1>
        <Button
          type="primary"
          icon={<PlusOutlined />}
          onClick={handleCreate}
          size="large"
        >
          Add Subject
        </Button>
      </div>

      <Card>
        <Table
          columns={columns}
          dataSource={subjects}
          loading={loading}
          rowKey="id"
          scroll={{ x: 'max-content' }}
        />
      </Card>

      <Modal
        title={editingSubject ? 'Edit Subject' : 'Create Subject'}
        open={isModalVisible}
        onCancel={() => {
          setIsModalVisible(false);
          form.resetFields();
        }}
        footer={null}
        width={500}
      >
        <Form
          form={form}
          layout="vertical"
          onFinish={handleSubmit}
        >
          <Form.Item
            name="name"
            label="Subject Name"
            rules={[{ required: true, message: 'Please enter subject name' }]}
          >
            <Input placeholder="Subject Name" size="large" />
          </Form.Item>

          <Form.Item className="mb-0">
            <Space className="w-full justify-end">
              <Button onClick={() => setIsModalVisible(false)} size="large">
                Cancel
              </Button>
              <Button type="primary" htmlType="submit" size="large">
                {editingSubject ? 'Update' : 'Create'}
              </Button>
            </Space>
          </Form.Item>
        </Form>
      </Modal>
    </div>
  );
};

export default ManageSubjects;
