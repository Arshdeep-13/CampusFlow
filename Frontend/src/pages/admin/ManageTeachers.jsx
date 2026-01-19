import { useState, useEffect } from 'react';
import { Table, Button, Modal, Form, Input, message, Space, Popconfirm, Card, Row, Col } from 'antd';
import { PlusOutlined, EditOutlined, DeleteOutlined } from '@ant-design/icons';
import { adminService } from '../../services/adminService';

const ManageTeachers = () => {
  const [teachers, setTeachers] = useState([]);
  const [loading, setLoading] = useState(false);
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [editingTeacher, setEditingTeacher] = useState(null);
  const [form] = Form.useForm();

  const fetchTeachers = async () => {
    setLoading(true);
    try {
      const response = await adminService.getTeachers();
      setTeachers(response);
    } catch (err) {
      message.error('Failed to fetch teachers');
      console.error('Error fetching teachers:', err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchTeachers();
  }, []);

  const handleCreate = () => {
    setEditingTeacher(null);
    form.resetFields();
    setIsModalVisible(true);
  };

  const handleEdit = (record) => {
    setEditingTeacher(record);
    form.setFieldsValue({
      name: record.name,
    });
    setIsModalVisible(true);
  };

  const handleDelete = async (teacherId) => {
    try {
      await adminService.deleteTeacher(teacherId);
      message.success('Teacher deleted successfully');
      fetchTeachers();
    } catch (error) {
      message.error(error.response?.data?.message || 'Failed to delete teacher');
    }
  };

  const handleSubmit = async (values) => {
    try {
      if (editingTeacher) {
        await adminService.editTeacher(editingTeacher.id, values);
        message.success('Teacher updated successfully');
      } else {
        await adminService.createTeacher(values);
        message.success('Teacher created successfully');
      }
      setIsModalVisible(false);
      form.resetFields();
      fetchTeachers();
    } catch (error) {
      message.error(error.response?.data?.message || 'Operation failed');
    }
  };

  const columns = [
    {
      title: 'ID',
      dataIndex: 'id',
      key: 'id',
      width: '20%',
      align: 'center',
    },
    {
      title: 'Name',
      dataIndex: 'name',
      key: 'name',
      width: '50%',
      align: 'center',
    },
    {
      title: 'Actions',
      key: 'actions',
      width: '30%',
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
            title="Are you sure you want to delete this teacher?"
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
        <h1 className="text-2xl font-bold">Manage Teachers</h1>
        <Button
          type="primary"
          icon={<PlusOutlined />}
          onClick={handleCreate}
          size="large"
        >
          Add Teacher
        </Button>
      </div>

      <Card>
        <Table
          columns={columns}
          dataSource={teachers}
          loading={loading}
          rowKey="id"
          scroll={{ x: 'max-content' }}
        />
      </Card>

      <Modal
        title={editingTeacher ? 'Edit Teacher' : 'Create Teacher'}
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
            label="Name"
            rules={[{ required: true, message: 'Please enter teacher name' }]}
          >
            <Input placeholder="Teacher Name" size="large" />
          </Form.Item>

          {!editingTeacher && (
            <Form.Item
              name="password"
              label="Password"
              rules={[{ required: true, message: 'Please enter password' }]}
            >
              <Input.Password placeholder="Password" size="large" />
            </Form.Item>
          )}

          <Form.Item className="mb-0">
            <Space className="w-full justify-end">
              <Button onClick={() => setIsModalVisible(false)} size="large">
                Cancel
              </Button>
              <Button type="primary" htmlType="submit" size="large">
                {editingTeacher ? 'Update' : 'Create'}
              </Button>
            </Space>
          </Form.Item>
        </Form>
      </Modal>
    </div>
  );
};

export default ManageTeachers;
